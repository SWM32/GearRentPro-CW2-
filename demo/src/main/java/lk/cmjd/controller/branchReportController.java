package lk.cmjd.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.branchReportDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.dto.tm.branchReportTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.branchReportService;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.custom.manageRentalService;
import lk.cmjd.util.sessionUtil;
import lk.cmjd.service.serviceFactory.serviceType;

public class branchReportController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnEquipmentReport;

    @FXML
    private ComboBox<branchDto> cbxBranch;

    @FXML
    private TableColumn<branchReportTM, String> colBID;

    @FXML
    private TableColumn<branchReportTM, Double> colDamage;

    @FXML
    private TableColumn<branchReportTM, Double> colLateFee;

    @FXML
    private TableColumn<branchReportTM, String> colName;

    @FXML
    private TableColumn<branchReportTM, Integer> colRentals;

    @FXML
    private TableColumn<branchReportTM, Double> colRevenue;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private Label lblRP;

    @FXML
    private TableView<branchReportTM> tblData;

    @FXML
    private TextField txtBranchSearchbar;

    private branchReportService branchReportService = (branchReportService) serviceFactory.getInstance()
            .getService(serviceType.BRANCH_REPORT);

    private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    private manageRentalService rentalService = (manageRentalService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RENTAL);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();
        loadBranches();

        String datePattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        dateStart.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateFormatter);
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });

        dateEnd.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateFormatter);
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });

        dateEnd.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (dateStart.getValue() == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please Select Start Date");
                    alert.show();
                    dateEnd.setValue(null);
                } else {
                    if (!newValue.isAfter(dateStart.getValue())) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid End Date");
                        alert.show();
                        dateEnd.setValue(null);
                    }
                }
            }
        });

        dateStart.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (dateEnd.getValue() != null && newValue.isAfter(dateEnd.getValue())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid End Date");
                    alert.show();
                    dateStart.setValue(null);
                    dateEnd.setValue(null);
                }
            }
        });

        btnCalculate.setOnAction(event -> {
            if (cbxBranch.getValue() == null || dateStart.getValue() == null || dateStart.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            try {
                ArrayList<rentalDto> renDtos = rentalService.getAll();

                double totalRev = 0;

                for (rentalDto dto : renDtos) {
                    if (dto.getRental_status().equals("RETURNED")
                            && dto.getBranch_id().equals(cbxBranch.getValue().getBranchID())) {
                        if (dto.getActual_return_date().isBefore(dateEnd.getValue())
                                && dto.getActual_return_date().isAfter(dateStart.getValue())) {
                            totalRev += dto.getFinal_pay();
                        }
                    }
                }

                lblRP.setText(String.format("%,.2f", totalRev));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnClear.setOnAction(event -> clearForm());
    }

    private void TableSetup() {
        colBID.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRentals.setCellValueFactory(new PropertyValueFactory<>("noOfRentals"));
        colRevenue.setCellValueFactory(new PropertyValueFactory<>("total_revenue"));
        colLateFee.setCellValueFactory(new PropertyValueFactory<>("total_late"));
        colDamage.setCellValueFactory(new PropertyValueFactory<>("total_damage"));

        try {
            ArrayList<branchReportDto> dtos = branchReportService.getAll();

            String sessionBranch = sessionUtil.getSession().getBranch();

            ObservableList<branchReportTM> obList = FXCollections.observableArrayList();

            for (branchReportDto dto : dtos) {
                if (sessionBranch == null || dto.getBranch_id().equals(sessionBranch)) {
                    obList.add(new branchReportTM(dto.getBranch_id(), dto.getName(), dto.getNoOfRentals(),
                            dto.getTotal_revenue(), dto.getTotal_late(), dto.getTotal_damage()));
                }
            }

            FilteredList<branchReportTM> filteredList = new FilteredList<>(obList, b -> true);

            txtBranchSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(branch -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (branch.getBranch_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(branch.getNoOfRentals()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(branch.getTotal_revenue()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(branch.getTotal_late()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(branch.getTotal_damage()).contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<branchReportTM> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tblData.comparatorProperty());

            tblData.setItems(sortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadBranches() {
        String sessionBranch = sessionUtil.getSession().getBranch();
        try {
            ArrayList<branchDto> branches = branchService.getAll();
            ObservableList<branchDto> obList = FXCollections.observableArrayList(branches);

            if (sessionBranch == null) {
                cbxBranch.setItems(obList);
            } else {
                for (branchDto branch : branches) {
                    if (branch.getBranchID().equals(sessionBranch)) {
                        cbxBranch.setValue(branch);
                        break;
                    }
                }
            }
            cbxBranch.setVisibleRowCount(4);

            cbxBranch.setConverter(new StringConverter<branchDto>() {

                @Override
                public branchDto fromString(String string) {
                    return null;
                }

                @Override
                public String toString(branchDto branch) {
                    return branch == null ? "" : branch.getBranchID() + "(" + branch.getName() + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearForm() {
        if (sessionUtil.getSession().getBranch() == null) {
            cbxBranch.setValue(null);
        }
        dateStart.setValue(null);
        dateEnd.setValue(null);
        lblRP.setText("");
    }

}
