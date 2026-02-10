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
import javafx.util.StringConverter;
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
import lk.cmjd.db.DBConnection;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.customerDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.dto.tm.manageEquipmentTM;
import lk.cmjd.dto.tm.manageRentalTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.service.custom.itemCategoryService;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.custom.manageCustomerService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.custom.manageRentalService;
import lk.cmjd.service.custom.manageReservationService;
import lk.cmjd.service.custom.membershipDiscountService;
import lk.cmjd.util.rentalUtil;
import lk.cmjd.util.sessionUtil;

public class manageRentalController implements Initializable {

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
        private ComboBox<String> cbxPayStatus;

        @FXML
        private ComboBox<String> cbxRentStatus;

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
        private DatePicker dateDue;

        @FXML
        private TableView<manageEquipmentTM> tblEquipmentData;

        @FXML
        private TableView<manageRentalTM> tblRentalData;

        @FXML
        private TextField txtCusID;

        @FXML
        private TextField txtEqSearchbar;

        @FXML
        private TextField txtREID;

        @FXML
        private TextField txtRenSearchbar;

        private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
                        .getService(serviceType.MANAGE_BRANCH);

        private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
                        .getService(serviceType.MANAGE_EQUIPMENT);

        private membershipDiscountService membershipService = (membershipDiscountService) serviceFactory.getInstance()
                        .getService(serviceType.MEMBERSHIP_DISCOUNT);

        private itemCategoryService categoryService = (itemCategoryService) serviceFactory.getInstance()
                        .getService(serviceType.ITEM_CATEGORY);

        private manageRentalService rentalService = (manageRentalService) serviceFactory.getInstance()
                        .getService(serviceType.MANAGE_RENTAL);

        private manageReservationService reservationService = (manageReservationService) serviceFactory.getInstance()
                        .getService(serviceType.MANAGE_RESERVATION);

        private manageCustomerService customerService = (manageCustomerService) serviceFactory.getInstance()
                        .getService(serviceType.MANAGE_CUSTOMER);

        @Override
        public void initialize(URL arg0, ResourceBundle arg1) {
                RenTableSetup();
                EqTableSetup();
                loadBranches();
                loadEquipment();

                cbxPayStatus.getItems().addAll("PAID", "PARTIALLY_PAID", "UNPAID");

                cbxRentStatus.getItems().addAll("ACTIVE", "RETURNED", "OVERDUE", "CANCELLED");

                cbxBranch.valueProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                                EqTableSetup();
                                loadEquipment();
                        }
                });

                String datePattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

                dateDue.setConverter(new StringConverter<LocalDate>() {
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

                dateDue.valueProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                                rentalUtil.endDateValidation(LocalDate.now(), dateDue);
                        }
                });

                tblRentalData.getSelectionModel().selectedItemProperty()
                                .addListener((observable, oldValue, newValue) -> {
                                        if (newValue != null) {
                                                txtREID.setText(newValue.getRental_id());
                                                txtCusID.setText(newValue.getCustomer_id());

                                                for (branchDto branch : cbxBranch.getItems()) {
                                                        if (branch.getBranchID().equals(newValue.getBranch_id())) {
                                                                cbxBranch.setValue(branch);
                                                                break;
                                                        }
                                                }

                                                for (equipmentDto equipment : cbxEquipment.getItems()) {
                                                        if (equipment.getEquipment_id()
                                                                        .equals(newValue.getEquipment_id())) {
                                                                cbxEquipment.setValue(equipment);
                                                        }
                                                }
                                                cbxPayStatus.setValue(newValue.getPayment_status());
                                                cbxRentStatus.setValue(newValue.getRental_status());
                                                dateDue.setValue(newValue.getDue_date());

                                        }
                                });

                btnAdd.setOnAction(event -> {
                        if (txtREID.getText().isBlank() || txtCusID.getText().isBlank() || cbxBranch.getValue() == null
                                        || cbxEquipment.getValue() == null || cbxPayStatus.getValue() == null
                                        || cbxRentStatus.getValue() == null || dateDue.getValue() == null) {

                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("ERROR");
                                alert.setContentText("Please Fill the Essential Details");
                                alert.show();
                                return;
                        }

                        String rental_id = txtREID.getText();
                        String customer_id = txtCusID.getText();
                        String equipment_id = cbxEquipment.getValue().getEquipment_id();
                        String branch_id = cbxBranch.getValue().getBranchID();
                        LocalDate start_date = LocalDate.now();
                        LocalDate due_date = dateDue.getValue();
                        LocalDate actual_return_date = null;
                        float total_rent = 0;
                        float sdh = 0;
                        float mdh = 0;
                        float lrd = 0;
                        float final_pay = 0;
                        String payment_status = cbxPayStatus.getValue();
                        String rental_status = cbxRentStatus.getValue();

                        equipmentDto eq;
                        itemCategoryDto cat;
                        customerDto cus;
                        membershipDiscountDto mem;

                        float totalRent;
                        String eqStatus;
                        float cusDeposit;
                        float maxDeposit;
                        long rentalDays = ChronoUnit.DAYS.between(start_date, due_date);

                        try {
                                eq = equipmentService.search(equipment_id);
                                cat = categoryService.search(eq.getCategory_id());
                                cus = customerService.search(customer_id);
                                mem = membershipService.search(cus.getMid());

                                eqStatus = eq.getStatus();
                                cusDeposit = cus.getDep();
                                maxDeposit = mem.getMaxDep();

                                totalRent = rentalService.totalRent(eq, cat, start_date, due_date);
                                total_rent = totalRent;

                                sdh = eq.getSda();
                                mdh = total_rent * mem.getDiscount() / 100;

                                if (rentalDays >= 7) {
                                        lrd = total_rent * cat.getLRDR() / 100;
                                }

                                final_pay = total_rent - (mdh + lrd);

                        } catch (Exception e) {
                                e.printStackTrace();
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setContentText("Error calculating rental details");
                                alert.show();
                                return;
                        }

                        rentalDto dto = new rentalDto(rental_id, customer_id, equipment_id, branch_id, start_date,
                                        due_date, actual_return_date, total_rent, sdh, mdh, lrd, final_pay,
                                        payment_status, rental_status);

                        if ((!eqStatus.equals("RENTED") || !eqStatus.equals("MAINTENANCE"))
                                        && cusDeposit <= maxDeposit
                                        && rentalUtil.RentalDateOverlapValidation(start_date, due_date, equipment_id)
                                        && rentalUtil.ReserDateOverlapValidation(start_date, due_date, equipment_id)) {
                                try {
                                        Connection connection = DBConnection.getInstance().getConnection();

                                        try {
                                                connection.setAutoCommit(false);

                                                if (rentalService.save(dto)) {
                                                        boolean isEquipmentStatusUpdated = true;

                                                        equipmentDto edto = equipmentService.search(equipment_id);

                                                        if (edto != null) {
                                                                equipmentDto udto = new equipmentDto(
                                                                                edto.getEquipment_id(),
                                                                                edto.getBranch_id(),
                                                                                edto.getCategory_id(), edto.getBrand(),
                                                                                edto.getModel(), edto.getYear(),
                                                                                edto.getBdp(), edto.getSda(), "RENTED");
                                                                isEquipmentStatusUpdated = equipmentService
                                                                                .update(udto);
                                                        } else {
                                                                isEquipmentStatusUpdated = false;
                                                        }

                                                        if (isEquipmentStatusUpdated) {
                                                                boolean isCustomerDepositUpdated = true;

                                                                customerDto uCusDto = new customerDto(cus.getCusId(),
                                                                                cus.getName(), cus.getNic_pass(),
                                                                                cus.getContact(), cus.getEmail(),
                                                                                cus.getAddress(), cus.getMid(),
                                                                                (cus.getDep() + sdh));

                                                                isCustomerDepositUpdated = customerService
                                                                                .update(uCusDto);

                                                                if (isCustomerDepositUpdated) {
                                                                        connection.commit();
                                                                        Alert alert = new Alert(AlertType.INFORMATION);
                                                                        alert.setTitle("Success!");
                                                                        alert.setContentText(
                                                                                        "New Rental Added Successfully!");
                                                                        alert.show();
                                                                        clearForm();
                                                                        RenTableSetup();
                                                                        EqTableSetup();

                                                                } else {
                                                                        connection.rollback();
                                                                        Alert alert = new Alert(AlertType.ERROR);
                                                                        alert.setTitle("Fail");
                                                                        alert.setContentText(
                                                                                        "Failed to Update Customer Deposit");
                                                                        alert.show();
                                                                }

                                                        } else {
                                                                connection.rollback();
                                                                Alert alert = new Alert(AlertType.ERROR);
                                                                alert.setTitle("Fail");
                                                                alert.setContentText(
                                                                                "Failed to Update Equipment Status");
                                                                alert.show();
                                                        }

                                                } else {
                                                        connection.rollback();
                                                        Alert alert = new Alert(AlertType.ERROR);
                                                        alert.setTitle("Fail");
                                                        alert.setContentText("Failed to Save Rental Details");
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
                                        Alert alert = new Alert(AlertType.ERROR);
                                        alert.setTitle("Fail");
                                        alert.setContentText(e.getMessage());
                                        alert.show();
                                }
                        } else {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Fail");
                                alert.setContentText(
                                                "Couldn't Add the Rental. Check Equipment Status and Customer Deposit Limit");
                                alert.show();
                        }

                });

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
                                        obList.add(new manageEquipmentTM(dto.getEquipment_id(), dto.getBranch_id(),
                                                        dto.getCategory_id(),
                                                        dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(),
                                                        dto.getSda(),
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
                        ArrayList<rentalDto> dtos = rentalService.getAll();

                        ObservableList<manageRentalTM> obList = FXCollections.observableArrayList();
                        for (rentalDto dto : dtos) {
                                obList.add(new manageRentalTM(dto.getRental_id(), dto.getCustomer_id(),
                                                dto.getEquipment_id(), dto.getBranch_id(), dto.getStart_date(),
                                                dto.getDue_date(), dto.getActual_return_date(), dto.getTotal_rent(),
                                                dto.getSdh(), dto.getMdh(), dto.getLrd(), dto.getFinal_pay(),
                                                dto.getPayment_status(), dto.getRental_status()));
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

        public void clearForm() {
                txtREID.setText("");
                txtCusID.setText("");
                dateDue.setValue(null);
                if (sessionUtil.getSession().getBranch() == null) {
                        cbxBranch.setValue(null);
                }
                cbxEquipment.setValue(null);
                cbxPayStatus.setValue(null);
                cbxRentStatus.setValue(null);
        }
}
