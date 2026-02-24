package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.StringConverter;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.dto.tm.manageEquipmentTM;
import lk.cmjd.dto.tm.manageRentalTM;
import lk.cmjd.dto.tm.manageReservationTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.custom.manageRentalService;
import lk.cmjd.service.custom.manageReservationService;
import lk.cmjd.util.sessionUtil;

public class equipmentReportController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnBranchReport;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<branchDto> cbxBranch;

    @FXML
    private ComboBox<equipmentDto> cbxEquipment;

    @FXML
    private TableColumn<manageEquipmentTM, Float> colEqBDP;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqBID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqBrand;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqCID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqEID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqModel;

    @FXML
    private TableColumn<manageEquipmentTM, Float> colEqSDA;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEqStatus;

    @FXML
    private TableColumn<manageEquipmentTM, Integer> colEqYear;

    @FXML
    private TableColumn<manageRentalTM, LocalDate> colRenARD;

    @FXML
    private TableColumn<manageRentalTM, String> colRenBID;

    @FXML
    private TableColumn<manageRentalTM, String> colRenCusID;

    @FXML
    private TableColumn<manageRentalTM, LocalDate> colRenDue;

    @FXML
    private TableColumn<manageRentalTM, String> colRenEID;

    @FXML
    private TableColumn<manageRentalTM, Float> colRenFP;

    @FXML
    private TableColumn<manageRentalTM, Float> colRenLRD;

    @FXML
    private TableColumn<manageRentalTM, Float> colRenMD;

    @FXML
    private TableColumn<manageRentalTM, String> colRenPS;

    @FXML
    private TableColumn<manageRentalTM, String> colRenREID;

    @FXML
    private TableColumn<manageRentalTM, String> colRenRS;

    @FXML
    private TableColumn<manageRentalTM, Float> colRenSDH;

    @FXML
    private TableColumn<manageRentalTM, LocalDate> colRenStart;

    @FXML
    private TableColumn<manageRentalTM, Float> colRenTR;

    @FXML
    private TableColumn<manageReservationTM, String> colResBID;

    @FXML
    private TableColumn<manageReservationTM, String> colResCusID;

    @FXML
    private TableColumn<manageReservationTM, LocalDate> colResDate;

    @FXML
    private TableColumn<manageReservationTM, String> colResEID;

    @FXML
    private TableColumn<manageReservationTM, LocalDate> colResEnd;

    @FXML
    private TableColumn<manageReservationTM, String> colResRSID;

    @FXML
    private TableColumn<manageReservationTM, LocalDate> colResStart;

    @FXML
    private TableColumn<manageReservationTM, String> colResStatus;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private Label lblRen;

    @FXML
    private Label lblRes;

    @FXML
    private TableView<manageEquipmentTM> tblEquipmentData;

    @FXML
    private TableView<manageRentalTM> tblRentalData;

    @FXML
    private TableView<manageReservationTM> tblReservationData;

    @FXML
    private TextField txtEqSearchbar;

    @FXML
    private TextField txtRenSearchbar;

    @FXML
    private TextField txtResSearchbar;

    private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_EQUIPMENT);

    private manageRentalService rentalService = (manageRentalService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RENTAL);

    private manageReservationService reservationService = (manageReservationService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RESERVATION);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadBranches();
        loadEquipment();
        EqTableSetup();

        cbxBranch.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EqTableSetup();
                loadEquipment();
            }
        });

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

        btnSearch.setOnAction(event -> {
            if (cbxBranch.getValue() == null || cbxEquipment.getValue() == null || dateStart.getValue() == null
                    || dateEnd.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            RenTableSetup();
            ResTableSetup();

            int rentals = tblRentalData.getItems().size();
            int reservation = tblReservationData.getItems().size();

            lblRen.setText(String.valueOf(rentals));
            lblRes.setText(String.valueOf(reservation));

        });

        btnClear.setOnAction(event -> clearForm());

        btnBranchReport.setOnAction(event -> loadContent("/lk/cmjd/BranchReport.fxml"));
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
                    return branch == null ? ""
                            : branch.getBranchID() + "(" + branch.getName() + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadEquipment() {
        String branch = cbxBranch.getValue() != null ? cbxBranch.getValue().getBranchID() : null;
        try {
            ArrayList<equipmentDto> equipments = equipmentService.getAll();
            ObservableList<equipmentDto> obList = FXCollections.observableArrayList();

            if (branch == null) {
                for (equipmentDto equipment : equipments) {
                    obList.add(equipment);
                }
            } else {
                for (equipmentDto equipment : equipments) {
                    if (equipment.getBranch_id().equals(branch)) {
                        obList.add(equipment);
                    }
                }
            }

            cbxEquipment.setItems(obList);
            cbxEquipment.setVisibleRowCount(4);

            cbxEquipment.setConverter(new StringConverter<equipmentDto>() {

                @Override
                public equipmentDto fromString(String arg0) {
                    return null;
                }

                @Override
                public String toString(equipmentDto equipment) {
                    return equipment == null ? ""
                            : equipment.getEquipment_id() + "(" + equipment.getBrand()
                                    + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void RenTableSetup() {
        colRenREID.setCellValueFactory(new PropertyValueFactory<>("rental_id"));
        colRenCusID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colRenEID.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
        colRenBID.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colRenStart.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        colRenDue.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        colRenARD.setCellValueFactory(new PropertyValueFactory<>("actual_return_date"));
        colRenFP.setCellValueFactory(new PropertyValueFactory<>("final_pay"));
        colRenTR.setCellValueFactory(new PropertyValueFactory<>("total_rent"));
        colRenSDH.setCellValueFactory(new PropertyValueFactory<>("sdh"));
        colRenMD.setCellValueFactory(new PropertyValueFactory<>("mdh"));
        colRenLRD.setCellValueFactory(new PropertyValueFactory<>("lrd"));
        colRenPS.setCellValueFactory(new PropertyValueFactory<>("payment_status"));
        colRenRS.setCellValueFactory(new PropertyValueFactory<>("rental_status"));

        try {
            String branch = cbxBranch.getValue() != null ? cbxBranch.getValue().getBranchID() : null;
            String equipment = cbxEquipment.getValue() != null ? cbxEquipment.getValue().getEquipment_id() : null;
            LocalDate uStart = dateStart.getValue();
            LocalDate uEnd = dateEnd.getValue();

            ArrayList<rentalDto> dtos = rentalService.getAll();

            ObservableList<manageRentalTM> obList = FXCollections.observableArrayList();
            for (rentalDto dto : dtos) {
                if (dto.getBranch_id().equals(branch) && dto.getEquipment_id().equals(equipment)
                        && ((!dto.getStart_date().isBefore(uStart) && !dto.getStart_date().isAfter(uEnd))
                                || (!dto.getDue_date().isBefore(uStart) && !dto.getDue_date().isAfter(uEnd)))) {
                    obList.add(new manageRentalTM(dto.getRental_id(), dto.getCustomer_id(),
                            dto.getEquipment_id(), dto.getBranch_id(), dto.getStart_date(),
                            dto.getDue_date(), dto.getActual_return_date(),
                            dto.getTotal_rent(),
                            dto.getSdh(), dto.getMdh(), dto.getLrd(), dto.getFinal_pay(),
                            dto.getPayment_status(), dto.getRental_status()));
                }
            }

            FilteredList<manageRentalTM> filteredData = new FilteredList<>(obList, b -> true);

            txtRenSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(rental -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (rental.getRental_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental.getCustomer_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental.getEquipment_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental.getBranch_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getStart_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getDue_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getActual_return_date())
                            .contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getTotal_rent()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getSdh()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getMdh()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getLrd()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental.getFinal_pay()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental.getPayment_status().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental.getRental_status().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<manageRentalTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblRentalData.comparatorProperty());

            tblRentalData.setItems(sortedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ResTableSetup() {
        colResRSID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colResCusID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colResEID.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
        colResBID.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colResDate.setCellValueFactory(new PropertyValueFactory<>("revervation_date"));
        colResStart.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        colResEnd.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        colResStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            String branch = cbxBranch.getValue() != null ? cbxBranch.getValue().getBranchID() : null;
            String equipment = cbxEquipment.getValue() != null ? cbxEquipment.getValue().getEquipment_id() : null;
            LocalDate uStart = dateStart.getValue();
            LocalDate uEnd = dateEnd.getValue();

            ArrayList<reservationDto> dtos = reservationService.getAll();

            ObservableList<manageReservationTM> obList = FXCollections.observableArrayList();
            for (reservationDto dto : dtos) {
                if (dto.getBranch_id().equals(branch) && dto.getEquipment_id().equals(equipment)
                        && dto.getStatus().equals("PENDING")
                        && ((!dto.getStart_date().isBefore(uStart) && !dto.getStart_date().isAfter(uEnd))
                                || (!dto.getEnd_date().isBefore(uStart) && !dto.getEnd_date().isAfter(uEnd)))) {
                    obList.add(new manageReservationTM(dto.getReservation_id(), dto.getCustomer_id(),
                            dto.getEquipment_id(), dto.getBranch_id(), dto.getRevervation_date(), dto.getStart_date(),
                            dto.getEnd_date(), dto.getStatus()));
                }

            }

            FilteredList<manageReservationTM> filteredData = new FilteredList<>(obList, b -> true);

            txtResSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(reservation -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (reservation.getReservation_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (reservation.getCustomer_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (reservation.getEquipment_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (reservation.getBranch_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(reservation.getRevervation_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(reservation.getStart_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(reservation.getEnd_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (reservation.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<manageReservationTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblReservationData.comparatorProperty());

            tblReservationData.setItems(sortedData);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void EqTableSetup() {
        colEqEID.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
        colEqBID.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colEqCID.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colEqBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colEqModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colEqYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colEqBDP.setCellValueFactory(new PropertyValueFactory<>("bdp"));
        colEqSDA.setCellValueFactory(new PropertyValueFactory<>("sda"));
        colEqStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            String branch = cbxBranch.getValue() != null ? cbxBranch.getValue().getBranchID() : null;
            ArrayList<equipmentDto> dtos = equipmentService.getAll();

            ObservableList<manageEquipmentTM> obList = FXCollections.observableArrayList();
            for (equipmentDto dto : dtos) {
                if (branch == null || dto.getBranch_id().equals(branch)) {
                    obList.add(new manageEquipmentTM(dto.getEquipment_id(), dto.getBranch_id(), dto.getCategory_id(),
                            dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(), dto.getSda(),
                            dto.getStatus()));
                }
            }

            FilteredList<manageEquipmentTM> filteredData = new FilteredList<>(obList, b -> true);

            txtEqSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(equipment -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (equipment.getEquipment_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getBranch_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getCategory_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(equipment.getYear()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(equipment.getBdp()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(equipment.getSda()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<manageEquipmentTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblEquipmentData.comparatorProperty());

            tblEquipmentData.setItems(sortedData);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadContent(String file) {
        AnchorPane displayPane = null;
        try {
            displayPane = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ancrDisplay.getChildren().clear();
        ancrDisplay.getChildren().add(displayPane);
    }

    public void clearForm() {
        if (sessionUtil.getSession().getBranch() == null) {
            cbxBranch.setValue(null);
        }
        cbxEquipment.setValue(null);
        lblRen.setText("");
        lblRen.setText("");
    }
}
