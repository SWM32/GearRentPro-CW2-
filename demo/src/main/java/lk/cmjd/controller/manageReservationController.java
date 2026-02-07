package lk.cmjd.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.cmjd.db.DBConnection;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.dto.tm.manageEquipmentTM;
import lk.cmjd.dto.tm.manageReservationTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.custom.manageReservationService;
import lk.cmjd.util.sessionUtil;

public class manageReservationController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<branchDto> cbxBranch;

    @FXML
    private ComboBox<equipmentDto> cbxEquipment;

    @FXML
    private ComboBox<String> cbxStatus;

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
    private TableView<manageEquipmentTM> tblEquipmentData;

    @FXML
    private TableView<manageReservationTM> tblReservationData;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtEqSearchbar;

    @FXML
    private TextField txtRSID;

    @FXML
    private TextField txtResSearchbar;

    private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_EQUIPMENT);

    private manageReservationService reservationService = (manageReservationService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RESERVATION);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ResTableSetup();
        EqTableSetup();
        loadBranches();
        loadEquipment();

        cbxStatus.getItems().addAll("PENDING", "COMPLETED", "CANCELLED");

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

        dateStart.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                startDateValidation();
            }
        });

        dateEnd.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (dateStart.getValue() == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please Select Start Date");
                    alert.show();
                } else {
                    endDateValidation();
                }
            }
        });

        tblReservationData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtRSID.setText(newValue.getReservation_id());
                txtCusID.setText(newValue.getCustomer_id());

                for (branchDto branch : cbxBranch.getItems()) {
                    if (branch.getBranchID().equals(newValue.getBranch_id())) {
                        cbxBranch.setValue(branch);
                        break;
                    }
                }

                for (equipmentDto equipment : cbxEquipment.getItems()) {
                    if (equipment.getEquipment_id().equals(newValue.getEquipment_id())) {
                        cbxEquipment.setValue(equipment);
                    }
                }

                cbxStatus.setValue(newValue.getStatus());

                dateStart.setValue(newValue.getStart_date());
                dateEnd.setValue(newValue.getEnd_date());
            }
        });

        btnAdd.setOnAction(event -> {
            if (cbxBranch.getValue() == null || cbxEquipment.getValue() == null || cbxStatus.getValue() == null
                    || txtCusID.getText().isBlank() || txtRSID.getText().isBlank() || dateStart.getValue() == null
                    || dateEnd.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            String rsid = txtRSID.getText();
            String cusid = txtCusID.getText();
            String eid = cbxEquipment.getValue().getEquipment_id();
            String bid = cbxBranch.getValue().getBranchID();
            LocalDate reservationDate = LocalDate.now();
            LocalDate startDate = dateStart.getValue();
            LocalDate endDate = dateEnd.getValue();
            String status = cbxStatus.getValue();

            reservationDto dto = new reservationDto(rsid, cusid, eid, bid, reservationDate, startDate, endDate, status);

            if (dateOverlapValidation()) {
                try {
                    Connection connection = DBConnection.getInstance().getConnection();

                    try {
                        connection.setAutoCommit(false);

                        if (reservationService.save(dto)) {

                            boolean isEquipmentStatusUpdated = true;

                            equipmentDto edto = equipmentService.search(eid);

                            if (edto != null) {
                                equipmentDto udto = new equipmentDto(edto.getEquipment_id(), edto.getBranch_id(),
                                        edto.getCategory_id(), edto.getBrand(), edto.getModel(), edto.getYear(),
                                        edto.getBdp(), edto.getSda(), "RESERVED");
                                isEquipmentStatusUpdated = equipmentService.update(udto);
                            } else {
                                isEquipmentStatusUpdated = false;
                            }

                            if (isEquipmentStatusUpdated) {
                                connection.commit();
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Success!");
                                alert.setContentText("New Equipment Added Successfully!");
                                alert.show();
                                clearForm();
                                ResTableSetup();
                                EqTableSetup();
                            } else {
                                connection.rollback();
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Fail");
                                alert.setContentText("Failed to Update Equipment Status");
                                alert.show();
                            }

                        } else {
                            connection.rollback();
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Fail");
                            alert.setContentText("Failed to Save Reservation Details");
                            alert.show();
                        }

                    } catch (Exception e) {
                        connection.rollback();
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Fail");
                        alert.setContentText(e.getMessage());
                        alert.show();
                    } finally {
                        connection.setAutoCommit(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnCancel.setOnAction(event -> {
            if (txtRSID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Reservation ID");
                alert.show();
                return;
            }
            try {
                boolean success = reservationService.delete(txtRSID.getText());
                if (success) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Reservation Cancelled Successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText("Reservation Could not be cancelled");
                    alert.show();
                }
                clearForm();
                ResTableSetup();
                EqTableSetup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnUpdate.setOnAction((event) -> {
            String rsid = txtRSID.getText();
            String cusid = txtCusID.getText();
            String eid = cbxEquipment.getValue().getEquipment_id();
            String bid = cbxBranch.getValue().getBranchID();
            LocalDate reservationDate = LocalDate.now();
            LocalDate startDate = dateStart.getValue();
            LocalDate endDate = dateEnd.getValue();
            String status = cbxStatus.getValue();

            reservationDto dto = new reservationDto(rsid, cusid, eid, bid, reservationDate, startDate, endDate, status);

            if (dateOverlapValidation()) {
                try {
                    boolean success = reservationService.update(dto);
                    if (!success) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Reservation Could not be updated");
                        alert.show();
                        return;
                    }
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setContentText("Reservation Updated Successfully!");
                    alert.show();

                    clearForm();
                    ResTableSetup();
                    EqTableSetup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSearch.setOnAction(event -> {
            if (txtRSID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Reservation ID");
                alert.show();
                return;
            }
            try {
                reservationDto dto = reservationService.search(txtRSID.getText());
                if (dto == null) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Not Found");
                    alert.setContentText("Reservation Not Found");
                    alert.show();
                    return;
                }

                txtCusID.setText(dto.getCustomer_id());
                dateStart.setValue(dto.getStart_date());
                dateEnd.setValue(dto.getEnd_date());
                cbxStatus.setValue(dto.getStatus());

                for (branchDto branch : cbxBranch.getItems()) {
                    if (branch.getBranchID().equals(dto.getBranch_id())) {
                        cbxBranch.setValue(branch);
                        break;
                    }
                }

                for (equipmentDto equipment : cbxEquipment.getItems()) {
                    if (equipment.getEquipment_id().equals(dto.getEquipment_id())) {
                        cbxEquipment.setValue(equipment);
                        break;
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        btnClear.setOnAction(event -> clearForm());
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
            ArrayList<reservationDto> dtos = reservationService.getAll();

            ObservableList<manageReservationTM> obList = FXCollections.observableArrayList();
            for (reservationDto dto : dtos) {
                obList.add(new manageReservationTM(dto.getReservation_id(), dto.getCustomer_id(),
                        dto.getEquipment_id(), dto.getBranch_id(), dto.getRevervation_date(), dto.getStart_date(),
                        dto.getEnd_date(), dto.getStatus()));
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

    public void startDateValidation() {
        LocalDate today = LocalDate.now();
        LocalDate new_start = dateStart.getValue();

        if (!new_start.isAfter(today)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Starting Date");
            alert.show();

            dateStart.setValue(null);
        }
    }

    public void endDateValidation() {
        LocalDate new_start = dateStart.getValue();
        LocalDate new_end = dateEnd.getValue();

        if (new_end.compareTo(new_start) >= 0) {
            long daysBetween = ChronoUnit.DAYS.between(new_start, new_end);
            if (daysBetween > 30) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Rental Gap should be <= 30 Days");
                alert.show();

                dateEnd.setValue(null);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid End Date");
            alert.show();

            dateEnd.setValue(null);
        }
    }

    private boolean dateOverlapValidation() {
        LocalDate new_start = dateStart.getValue();
        LocalDate new_end = dateEnd.getValue();
        String res_eq_id = cbxEquipment.getValue().getEquipment_id();

        for (manageReservationTM res : tblReservationData.getItems()) {
            LocalDate exist_start = res.getStart_date();
            LocalDate exist_end = res.getEnd_date();

            if (res.getEquipment_id().equals(res_eq_id)) {
                if (new_start.isBefore(exist_start)) {
                    if (new_end.isAfter(exist_start)) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText(
                                "Reservation Overlap with Existing reservation (" + res.getReservation_id() + ")");
                        alert.show();

                        return false;
                    }
                } else if (new_start.isAfter(exist_start)) {
                    if (exist_end.isAfter(new_start)) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText(
                                "Reservation Overlap with Existing reservation (" + res.getReservation_id() + ")");
                        alert.show();

                        return false;
                    }
                } else if (new_start.compareTo(exist_start) == 0) {
                    return false;
                }
            }
        }

        return true;
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
                    return equipment == null ? "" : equipment.getEquipment_id() + "(" + equipment.getBrand() + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearForm() {
        txtRSID.setText("");
        txtCusID.setText("");
        dateStart.setValue(null);
        dateEnd.setValue(null);
        if (sessionUtil.getSession().getBranch() == null) {
            cbxBranch.setValue(null);
        }
        cbxEquipment.setValue(null);
        cbxStatus.setValue(null);
    }

}
