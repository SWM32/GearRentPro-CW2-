package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.db.DBConnection;
import lk.cmjd.dto.customerDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.dto.tm.manageReservationTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.service.custom.itemCategoryService;
import lk.cmjd.service.custom.manageCustomerService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.custom.manageRentalService;
import lk.cmjd.service.custom.manageReservationService;
import lk.cmjd.service.custom.membershipDiscountService;

public class resToRenController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnConvert;

    @FXML
    private Button btnMngRes;

    @FXML
    private ComboBox<String> cbxPayStatus;

    @FXML
    private ComboBox<String> cbxRentStatus;

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
    private TableView<manageReservationTM> tblReservationData;

    @FXML
    private TextField txtRSID;

    @FXML
    private TextField txtResSearchbar;

    private manageReservationService reservationService = (manageReservationService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RESERVATION);

    private manageRentalService rentalService = (manageRentalService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RENTAL);

    private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_EQUIPMENT);

    private membershipDiscountService membershipService = (membershipDiscountService) serviceFactory.getInstance()
            .getService(serviceType.MEMBERSHIP_DISCOUNT);

    private itemCategoryService categoryService = (itemCategoryService) serviceFactory.getInstance()
            .getService(serviceType.ITEM_CATEGORY);

    private manageCustomerService customerService = (manageCustomerService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_CUSTOMER);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ResTableSetup();

        cbxPayStatus.getItems().addAll("PAID", "PARTIALLY_PAID", "UNPAID");
        cbxRentStatus.getItems().addAll("ACTIVE", "RETURNED", "OVERDUE", "CANCELLED");

        tblReservationData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtRSID.setText(newValue.getReservation_id());
            }
        });

        btnConvert.setOnAction(event -> {
            if (txtRSID.getText().isBlank() || cbxPayStatus.getValue() == null || cbxRentStatus.getValue() == null) {

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            boolean validRSID = false;

            for (manageReservationTM res : tblReservationData.getItems()) {
                if (txtRSID.getText().equals(res.getReservation_id())) {
                    validRSID = true;
                }
            }

            if (validRSID) {
                reservationDto resDto = null;
                try {
                    resDto = reservationService.search(txtRSID.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String rental_id = newRenID();

                if (rental_id == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Error Generating Rental ID");
                    alert.show();
                    return;
                }

                if (resDto == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Error Retrieving Reservation Information");
                    alert.show();
                    return;
                }

                String customer_id = resDto.getCustomer_id();
                String equipment_id = resDto.getEquipment_id();
                String branch_id = resDto.getBranch_id();
                LocalDate start_date = resDto.getStart_date();
                LocalDate due_date = resDto.getEnd_date();
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

                if (!(eqStatus.equals("RENTED") || eqStatus.equals("MAINTENANCE"))
                        && cusDeposit <= maxDeposit) {
                    try {
                        Connection connection = DBConnection.getInstance().getConnection();

                        try {
                            connection.setAutoCommit(false);

                            if (rentalService.save(dto)) {
                                boolean isResStatusUpdated = true;

                                reservationDto newResDto = new reservationDto(resDto.getReservation_id(),
                                        resDto.getCustomer_id(), resDto.getEquipment_id(), resDto.getBranch_id(),
                                        resDto.getRevervation_date(), resDto.getStart_date(), resDto.getEnd_date(),
                                        "COMPLETED");

                                isResStatusUpdated = reservationService.update(newResDto);

                                if (isResStatusUpdated) {
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
                                            ResTableSetup();

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
                                    alert.setContentText(
                                            "Failed to Update Reservation Status");
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

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Invalid RSID");
                alert.show();
                return;
            }

        });

        btnMngRes.setOnAction((event) -> loadContent("/lk/cmjd/ManageReservation.fxml"));

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

            LocalDate today = LocalDate.now();

            for (reservationDto dto : dtos) {
                if (!dto.getStart_date().isAfter(today) && dto.getStatus().equals("PENDING")) {
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

    public String newRenID() {
        String lastRenID = null;
        try {
            lastRenID = rentalService.getLastID();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lastRenID == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Couldn't Retrieve Last Rental ID");
            alert.showAndWait();
            return null;
        }
        String prefix = "RE";
        String numStr = lastRenID.substring(prefix.length());

        int num = Integer.parseInt(numStr);
        num++;

        return String.format("%s%03d", prefix, num);
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
        txtRSID.setText("");
        cbxPayStatus.setValue(null);
        cbxRentStatus.setValue(null);
    }

}
