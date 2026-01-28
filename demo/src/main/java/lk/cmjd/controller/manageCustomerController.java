package lk.cmjd.controller;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.cmjd.dto.customerDto;
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.dto.tm.customerTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.manageCustomerService;
import lk.cmjd.service.custom.membershipDiscountService;
import lk.cmjd.service.serviceFactory.serviceType;

public class manageCustomerController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<membershipDiscountDto> cbxMembership;

    @FXML
    private TableColumn<customerTM, String> colAddress;

    @FXML
    private TableColumn<customerTM, String> colContact;

    @FXML
    private TableColumn<customerTM, String> colCusID;

    @FXML
    private TableColumn<customerTM, String> colEmail;

    @FXML
    private TableColumn<customerTM, String> colMID;

    @FXML
    private TableColumn<customerTM, String> colName;

    @FXML
    private TableColumn<customerTM, String> colNicPass;

    @FXML
    private TableView<customerTM> tblData;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNICPass;

    @FXML
    private TextField txtSearchbar;

    private manageCustomerService customerService = (manageCustomerService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_CUSTOMER);

    private membershipDiscountService membershipService = (membershipDiscountService) serviceFactory.getInstance()
            .getService(serviceType.MEMBERSHIP_DISCOUNT);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();
        loadMemberships();

        btnAdd.setOnAction(event -> {
            if (txtCusID.getText().isBlank() || txtName.getText().isBlank() || txtNICPass.getText().isBlank()
                    || txtContact.getText().isBlank() || txtEmail.getText().isBlank() || txtAddress.getText().isBlank()
                    || cbxMembership.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            String cusId = txtCusID.getText();
            String name = txtName.getText();
            String nicPass = txtNICPass.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            String mid = cbxMembership.getValue().getTierId();

            customerDto dto = new customerDto(cusId, name, nicPass, contact, email, address, mid);

            try {
                customerService.save(dto);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("New Customer Added Successfully!");
                alert.show();
                TableSetup();
                clearForm();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        btnUpdate.setOnAction((event) -> {
            String cusId = txtCusID.getText();
            String name = txtName.getText();
            String nicPass = txtNICPass.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            String mid = cbxMembership.getValue().getTierId();

            customerDto dto = new customerDto(cusId, name, nicPass, contact, email, address, mid);

            try {
                boolean success = customerService.update(dto);
                if (!success) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Customer Could not be updated");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Customer Updated Successfully!");
                alert.show();

                TableSetup();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnAction((event) -> {
            if (txtCusID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Customer ID");
                alert.show();
                return;
            }
            try {
                boolean success = customerService.delete(txtCusID.getText());
                if (success) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Customer Deleted Successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText("Customer Could not be deleted");
                    alert.show();
                }
                TableSetup();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnClear.setOnAction((event) -> clearForm());

        btnSearch.setOnAction((event) -> {
            if (txtCusID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Customer ID");
                alert.show();
                return;
            }
            try {
                customerDto dto = customerService.search(txtCusID.getText());
                if (dto == null) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Not Found");
                    alert.setContentText("Customer Not Found");
                    alert.show();
                    return;
                }

                txtName.setText(dto.getName());
                txtNICPass.setText(dto.getNic_pass());
                txtContact.setText(dto.getContact());
                txtEmail.setText(dto.getEmail());
                txtAddress.setText(dto.getAddress());

                // Set ComboBox value based on the search result
                for (membershipDiscountDto membership : cbxMembership.getItems()) {
                    if (membership.getTierId().equals(dto.getMid())) {
                        cbxMembership.setValue(membership);
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

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCusID.setText(newValue.getCusId());
                txtName.setText(newValue.getName());
                txtNICPass.setText(newValue.getNic_pass());
                txtContact.setText(newValue.getContact());
                txtEmail.setText(newValue.getEmail());
                txtAddress.setText(newValue.getAddress());

                for (membershipDiscountDto membership : cbxMembership.getItems()) {
                    if (membership.getTierId().equals(newValue.getMid())) {
                        cbxMembership.setValue(membership);
                        break;
                    }
                }
            }
        });
    }

    private void loadMemberships() {
        try {
            ArrayList<membershipDiscountDto> memberships = membershipService.getAll();
            ObservableList<membershipDiscountDto> obList = FXCollections.observableArrayList(memberships);

            cbxMembership.setItems(obList);
            cbxMembership.setVisibleRowCount(4);

            cbxMembership.setConverter(new StringConverter<membershipDiscountDto>() {

                @Override
                public membershipDiscountDto fromString(String string) {
                    return null;
                }

                @Override
                public String toString(membershipDiscountDto membership) {
                    return membership == null ? "" : membership.getTierId() + "(" + membership.getTiername() + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void TableSetup() {
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNicPass.setCellValueFactory(new PropertyValueFactory<>("nic_pass"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMID.setCellValueFactory(new PropertyValueFactory<>("mid"));

        try {
            ArrayList<customerDto> dtos = customerService.getAll();

            ObservableList<customerTM> obList = FXCollections.observableArrayList();
            for (customerDto dto : dtos) {
                obList.add(new customerTM(dto.getCusId(), dto.getName(), dto.getNic_pass(), dto.getContact(),
                        dto.getEmail(), dto.getAddress(), dto.getMid()));
            }

            FilteredList<customerTM> filteredData = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(customer -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (customer.getCusId().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getNic_pass().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getContact().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getMid().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<customerTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblData.comparatorProperty());

            tblData.setItems(sortedData);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void clearForm() {
        txtCusID.setText("");
        txtName.setText("");
        txtNICPass.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        cbxMembership.setValue(null);
    }

}
