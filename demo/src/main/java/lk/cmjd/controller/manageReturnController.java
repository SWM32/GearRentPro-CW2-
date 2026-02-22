package lk.cmjd.controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.db.DBConnection;
import lk.cmjd.dto.customerDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.dto.returnDto;
import lk.cmjd.dto.tm.manageRentalTM;
import lk.cmjd.dto.tm.manageReturnTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.util.sessionUtil;
import lk.cmjd.service.custom.itemCategoryService;
import lk.cmjd.service.custom.manageCustomerService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.custom.manageRentalService;
import lk.cmjd.service.custom.manageReservationService;
import lk.cmjd.service.custom.manageReturnService;

public class manageReturnController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnReturn;

    @FXML
    private CheckBox chbxDamaged;

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
    private TableColumn<manageReturnTM, Float> colRetDamageCharge;

    @FXML
    private TableColumn<manageReturnTM, Boolean> colRetDamaged;

    @FXML
    private TableColumn<manageReturnTM, String> colRetDescription;

    @FXML
    private TableColumn<manageReturnTM, Float> colRetLateFee;

    @FXML
    private TableColumn<manageReturnTM, String> colRetREID;

    @FXML
    private TableColumn<manageReturnTM, LocalDate> colRetReturnDate;

    @FXML
    private Label lblDamageCharge;

    @FXML
    private Label lblFinal;

    @FXML
    private Label lblLateDamage;

    @FXML
    private Label lblLateFee;

    @FXML
    private Label lblSDR;

    @FXML
    private TableView<manageReturnTM> tblReturnData;

    @FXML
    private TableView<manageRentalTM> tblRentalData;

    @FXML
    private TextArea txtADescription;

    @FXML
    private TextField txtREID;

    @FXML
    private TextField txtRenSearchbar;

    @FXML
    private TextField txtDamageCharge;

    @FXML
    private TextField txtRetSearchbar;

    private manageRentalService rentalService = (manageRentalService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RENTAL);

    private manageReturnService returnService = (manageReturnService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RETURN);

    private itemCategoryService categoryService = (itemCategoryService) serviceFactory.getInstance()
            .getService(serviceType.ITEM_CATEGORY);

    private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_EQUIPMENT);

    private manageReservationService reservationService = (manageReservationService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_RESERVATION);

    private manageCustomerService customerService = (manageCustomerService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_CUSTOMER);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        RetTableSetup();
        RenTableSetup();

        txtADescription.setDisable(true);
        txtDamageCharge.setDisable(true);

        chbxDamaged.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                txtADescription.setDisable(false);
                txtDamageCharge.setDisable(false);
            } else {
                txtADescription.clear();
                txtADescription.setDisable(true);
                txtDamageCharge.clear();
                txtDamageCharge.setDisable(true);
            }
        });

        tblRentalData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtREID.setText(newValue.getRental_id());
            }
        });

        tblReturnData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtREID.setText(newValue.getRental_Id());
                chbxDamaged.setSelected(newValue.isDamaged());
                txtDamageCharge.setText(String.valueOf(newValue.getDamage_charge()));
                txtADescription.setText(newValue.getDamage_description());

                lblLateFee.setText(String.valueOf(newValue.getLate_fee()));
                lblDamageCharge.setText(String.valueOf(newValue.getDamage_charge()));

                rentalDto rentaldto = null;
                try {
                    rentaldto = rentalService.search(newValue.getRental_Id());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                float[] fees = calculateFees(rentaldto);

                lblLateDamage.setText(String.valueOf(fees[2]));
                lblFinal.setText(String.valueOf(fees[3]));
                lblSDR.setText(String.valueOf(fees[4]));

            }
        });

        btnCalculate.setOnAction(event -> {
            if (txtREID.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Enter REID");
                alert.show();
                return;
            }

            rentalDto rentaldto = null;
            try {
                rentaldto = rentalService.search(txtREID.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (rentaldto == null || rentaldto.getRental_status().equals("RETURNED")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Not Found");
                alert.setContentText("Invalid Rental ID");
                alert.show();
                return;
            }

            float[] fees = calculateFees(rentaldto);

            lblLateFee.setText(String.valueOf(fees[0]));
            lblDamageCharge.setText(String.valueOf(fees[1]));
            lblLateDamage.setText(String.valueOf(fees[2]));
            lblFinal.setText(String.valueOf(fees[3]));
            lblSDR.setText(String.valueOf(fees[4]));

        });

        btnReturn.setOnAction(event -> {
            if (txtREID.getText().isBlank() || (chbxDamaged.isSelected()
                    && (txtADescription.getText().isBlank() || txtDamageCharge.getText().isBlank()))) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            rentalDto rentaldto = null;
            try {
                rentaldto = rentalService.search(txtREID.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (rentaldto == null || rentaldto.getRental_status().equals("RETURNED")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Not Found");
                alert.setContentText("Invalid Rental ID");
                alert.show();
                return;
            }

            float[] fees = calculateFees(rentaldto);

            returnDto dto = new returnDto(txtREID.getText(), LocalDate.now(), chbxDamaged.isSelected(), fees[0],
                    txtADescription.getText(), fees[1]);

            try {
                Connection connection = DBConnection.getInstance().getConnection();

                try {
                    connection.setAutoCommit(false);

                    if (returnService.save(dto)) {
                        boolean isRentalDetailsUpdated = true;

                        rentalDto updatedRentalDto = new rentalDto(rentaldto.getRental_id(), rentaldto.getCustomer_id(),
                                rentaldto.getEquipment_id(), rentaldto.getBranch_id(), rentaldto.getStart_date(),
                                rentaldto.getDue_date(), LocalDate.now(), rentaldto.getTotal_rent(), rentaldto.getSdh(),
                                rentaldto.getMdh(), rentaldto.getLrd(), rentaldto.getFinal_pay(), "PAID", "RETURNED");

                        isRentalDetailsUpdated = rentalService.update(updatedRentalDto);

                        if (isRentalDetailsUpdated) {
                            boolean isEquipmentStatusUpdated = true;

                            equipmentDto edto = equipmentService.search(updatedRentalDto.getEquipment_id());

                            if (edto != null) {
                                equipmentDto udto;
                                if (chbxDamaged.isSelected()) {
                                    udto = new equipmentDto(
                                            edto.getEquipment_id(),
                                            edto.getBranch_id(),
                                            edto.getCategory_id(), edto.getBrand(),
                                            edto.getModel(), edto.getYear(),
                                            edto.getBdp(), edto.getSda(), "MAINTENANCE");

                                } else {
                                    udto = new equipmentDto(
                                            edto.getEquipment_id(),
                                            edto.getBranch_id(),
                                            edto.getCategory_id(), edto.getBrand(),
                                            edto.getModel(), edto.getYear(),
                                            edto.getBdp(), edto.getSda(), "AVAILABLE");

                                    for (manageRentalTM ren : tblRentalData.getItems()) {
                                        if (!ren.getRental_id().equals(txtREID.getText())
                                                && ren.getEquipment_id().equals(edto.getEquipment_id())) {
                                            udto = new equipmentDto(
                                                    edto.getEquipment_id(),
                                                    edto.getBranch_id(),
                                                    edto.getCategory_id(), edto.getBrand(),
                                                    edto.getModel(), edto.getYear(),
                                                    edto.getBdp(), edto.getSda(), "RENTED");
                                            break;
                                        }
                                    }

                                    if (udto.getStatus().equals("AVAILABLE")) {
                                        ArrayList<reservationDto> resDtos = reservationService.getAll();

                                        for (reservationDto resDto : resDtos) {
                                            if (resDto.getEquipment_id().equals(edto.getEquipment_id())
                                                    && resDto.getStatus().equals("PENDING")) {
                                                udto = new equipmentDto(
                                                        edto.getEquipment_id(),
                                                        edto.getBranch_id(),
                                                        edto.getCategory_id(), edto.getBrand(),
                                                        edto.getModel(), edto.getYear(),
                                                        edto.getBdp(), edto.getSda(), "RESERVED");
                                                break;
                                            }
                                        }
                                    }
                                }
                                isEquipmentStatusUpdated = equipmentService.update(udto);

                            } else {
                                isEquipmentStatusUpdated = false;
                            }

                            if (isEquipmentStatusUpdated) {
                                boolean isCustomerDepositUpdated = true;

                                customerDto cus = customerService.search(updatedRentalDto.getCustomer_id());

                                customerDto uCusDto = new customerDto(cus.getCusId(),
                                        cus.getName(), cus.getNic_pass(),
                                        cus.getContact(), cus.getEmail(),
                                        cus.getAddress(), cus.getMid(),
                                        (cus.getDep() - fees[4]));

                                isCustomerDepositUpdated = customerService.update(uCusDto);

                                if (isCustomerDepositUpdated) {
                                    connection.commit();
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Success!");
                                    alert.setContentText(
                                            "Item Returned Successfully!");
                                    alert.show();
                                    clearForm();
                                    RetTableSetup();
                                    RenTableSetup();
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
                                alert.setContentText("Failed to Update Equipment Status");
                                alert.show();
                            }
                        } else {
                            connection.rollback();
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Fail");
                            alert.setContentText("Failed to Update Rental Details");
                            alert.show();
                        }
                    } else {
                        connection.rollback();
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Fail");
                        alert.setContentText("Failed to Save Return Details");
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

        });

        btnClear.setOnAction(event -> {
            clearForm();
        });
    }

    public void RetTableSetup() {
        colRetREID.setCellValueFactory(new PropertyValueFactory<>("rental_Id"));
        colRetReturnDate.setCellValueFactory(new PropertyValueFactory<>("actual_return_date"));
        colRetDamaged.setCellValueFactory(new PropertyValueFactory<>("damaged"));
        colRetLateFee.setCellValueFactory(new PropertyValueFactory<>("late_fee"));
        colRetDescription.setCellValueFactory(new PropertyValueFactory<>("damage_description"));
        colRetDamageCharge.setCellValueFactory(new PropertyValueFactory<>("damage_charge"));

        try {
            ArrayList<returnDto> dtos = returnService.getAll();

            ObservableList<manageReturnTM> obList = FXCollections.observableArrayList();

            for (returnDto dto : dtos) {
                obList.add(new manageReturnTM(dto.getRental_Id(), dto.getActual_return_date(), dto.isDamaged(),
                        dto.getLate_fee(), dto.getDamage_description(), dto.getDamage_charge()));
            }

            FilteredList<manageReturnTM> filteredData = new FilteredList<>(obList, b -> true);

            txtRetSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(rental_return -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (rental_return.getRental_Id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental_return.getActual_return_date()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental_return.isDamaged()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental_return.getLate_fee()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (rental_return.getDamage_description().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(rental_return).contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<manageReturnTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblReturnData.comparatorProperty());

            tblReturnData.setItems(sortedData);

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
            String branch = sessionUtil.getSession().getBranch();

            ArrayList<rentalDto> dtos = rentalService.getAll();

            ObservableList<manageRentalTM> obList = FXCollections.observableArrayList();
            for (rentalDto dto : dtos) {
                if (!dto.getRental_status().equals("RETURNED")
                        && (branch == null || dto.getBranch_id().equals(branch))) {
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

    private float[] calculateFees(rentalDto rental) {
        float[] fees = new float[5];
        float lateFee = 0;
        float sda = 0;
        float damageCharge;

        if (rental.getRental_status().equals("OVERDUE")) {
            long lateDays = ChronoUnit.DAYS.between(rental.getDue_date(), LocalDate.now());

            String categoryID;
            try {
                categoryID = equipmentService.search(rental.getEquipment_id()).getCategory_id();
                float lateFeePerDay = categoryService.search(categoryID).getLFPD();
                lateFee = lateDays * lateFeePerDay;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            sda = equipmentService.search(rental.getEquipment_id()).getSda();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (txtDamageCharge.getText().isBlank()) {
            damageCharge = 0;
        } else {
            damageCharge = Float.parseFloat(txtDamageCharge.getText());
        }

        float lateAndDamage = lateFee + damageCharge;
        float finalPay = rental.getFinal_pay();
        float sdReturn = sda - lateAndDamage;

        fees[0] = lateFee;
        fees[1] = damageCharge;
        fees[2] = lateAndDamage;
        fees[3] = finalPay;
        fees[4] = sdReturn;

        return fees;
    }

    public void clearForm() {
        txtREID.setText("");
        chbxDamaged.setSelected(false);
        lblLateFee.setText("---");
        lblDamageCharge.setText("---");
        lblLateDamage.setText("---");
        lblFinal.setText("---");
        lblSDR.setText("---");
    }
}
