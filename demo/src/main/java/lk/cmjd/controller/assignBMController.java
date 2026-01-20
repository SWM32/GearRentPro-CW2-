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
import lk.cmjd.dto.assignBranchDto;
import lk.cmjd.dto.tm.assignBranchTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.assignBranchService;
import lk.cmjd.service.serviceFactory.serviceType;

public class assignBMController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnManageBranch;

    @FXML
    private Button btnUnassign;

    @FXML
    private TableColumn<assignBranchTM, String> colBranchID;

    @FXML
    private TableColumn<assignBranchTM, String> colName;

    @FXML
    private TableColumn<assignBranchTM, String> colRole;

    @FXML
    private TableColumn<assignBranchTM, String> colUserID;

    @FXML
    private TableView<assignBranchTM> tblData;

    @FXML
    private TextField txtBranchID;

    @FXML
    private TextField txtSearchbar;

    @FXML
    private TextField txtUserID;

    private assignBranchService service = (assignBranchService) serviceFactory.getInstance()
            .getService(serviceType.ASSIGN_BRANCH);

    ObservableList<assignBranchTM> obList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();

        btnManageBranch.setOnAction(event -> loadContent("/lk/cmjd/ManageBranch.fxml"));

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtUserID.setText(newValue.getUserID());
                txtBranchID.setText(newValue.getBranchID());
            }
        });

        btnClear.setOnAction(event -> clearForm());

        btnAssign.setOnAction(event -> {
            if (txtUserID.getText().isEmpty() || txtBranchID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Both UserID and BranchID");
                alert.showAndWait();
            } else {
                boolean isManager = false;

                for (assignBranchTM item : obList) {
                    if (item.getUserID().toLowerCase().equals(txtUserID.getText().toLowerCase())) {
                        isManager = true;
                        break;
                    }
                }

                if (isManager) {
                    try {
                        service.assign(txtUserID.getText(), txtBranchID.getText());
                        TableSetup();
                        clearForm();
                    } catch (Exception e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Fail");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid UserID");
                    alert.showAndWait();
                }
            }
        });

        btnUnassign.setOnAction(event -> {
            if (txtUserID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please Enter UserID");
                alert.showAndWait();
            } else {
                boolean isManager = false;

                for (assignBranchTM item : obList) {
                    if (item.getUserID().toLowerCase().equals(txtUserID.getText().toLowerCase())) {
                        isManager = true;
                        break;
                    }
                }

                if (isManager) {
                    try {
                        service.assign(txtUserID.getText(), null);
                        TableSetup();
                        clearForm();
                    } catch (Exception e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Fail");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid UserID");
                    alert.showAndWait();
                }
            }
        });
    }

    public void TableSetup() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));

        try {
            ArrayList<assignBranchDto> dtos = service.getAll();

            obList = FXCollections.observableArrayList();

            for (assignBranchDto dto : dtos) {
                obList.add(new assignBranchTM(dto.getUserID(), dto.getName(), dto.getRole(), dto.getBranchID()));
            }

            FilteredList<assignBranchTM> filteredList = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(branch -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (branch.getUserID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getRole().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (branch.getBranchID() != null
                            && branch.getBranchID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });
            });

            SortedList<assignBranchTM> sortedData = new SortedList<>(filteredList);

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

    private void clearForm() {
        txtUserID.setText("");
        txtBranchID.setText("");
    }

}
