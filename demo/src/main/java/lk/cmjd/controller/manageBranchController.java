package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.tm.manageBranchTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.serviceFactory.serviceType;

public class manageBranchController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAssignBM;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<manageBranchTM, String> colAddress;

    @FXML
    private TableColumn<manageBranchTM, String> colContact;

    @FXML
    private TableColumn<manageBranchTM, String> colID;

    @FXML
    private TableColumn<manageBranchTM, String> colName;

    @FXML
    private TableView<manageBranchTM> tblData;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearchbar;

    private manageBranchService service = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();

        btnAdd.setOnAction((event) -> {
            String id = txtID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();

            branchDto dto = new branchDto(id, name, address, contact);

            try {
                service.save(dto);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("New Branch Added Successfully!");
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

        btnSearch.setOnAction((event) -> {
            try {
                branchDto dto = service.search(txtID.getText());
                if (dto == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Branch Not Found");
                    alert.showAndWait();
                    return;
                }
                txtName.setText(dto.getName());
                txtAddress.setText(dto.getAddress());
                txtContact.setText(dto.getContact());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnUpdate.setOnAction((event) -> {
            String id = txtID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();

            branchDto dto = new branchDto(id, name, address, contact);

            try {
                boolean success = service.update(dto);
                if (!success) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Branch Could not be updated");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Branch Updated Successfully!");
                alert.show();

                TableSetup();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        btnDelete.setOnAction((event) -> {
            if (txtID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Branch ID");
                alert.show();
                return;
            }
            try {
                boolean success = service.delete(txtID.getText());
                if (success) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Branch Deleted Successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText("Branch Could not be deleted");
                    alert.show();
                }
                TableSetup();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnClear.setOnAction((event) -> clearForm());

        btnAssignBM.setOnAction((event) -> loadContent("/lk/cmjd/AssignBM.fxml"));

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
            }
        });
    }

    public void TableSetup() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            ArrayList<branchDto> dtos = service.getAll();

            ObservableList<manageBranchTM> obList = FXCollections.observableArrayList();
            for (branchDto dto : dtos) {
                obList.add(new manageBranchTM(dto.getBranchID(), dto.getName(), dto.getAddress(), dto.getContact()));
            }

            // Wrap the ObservableList in a FilteredList (initially display all data)
            FilteredList<manageBranchTM> filteredData = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(branch -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (branch.getId().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getContact().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            // Wrap the FilteredList in a SortedList.
            // Otherwise, sorting the table by clicking headers won't work.
            SortedList<manageBranchTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblData.comparatorProperty());

            tblData.setItems(sortedData);

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
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

}
