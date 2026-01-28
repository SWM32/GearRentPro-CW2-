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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.cmjd.dto.assignBranchDto;
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.tm.assignBranchTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.assignBranchService;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.serviceFactory.serviceType;

public class assignBMController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnAssign;

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
    private ComboBox<branchDto> cbxBranch;

    @FXML
    private ComboBox<assignBranchTM> cbxManager;

    @FXML
    private TextField txtSearchbar;

    private assignBranchService assignService = (assignBranchService) serviceFactory.getInstance()
            .getService(serviceType.ASSIGN_BRANCH);

    private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    ObservableList<assignBranchTM> obList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();
        loadManagers();
        loadBranches();

        btnManageBranch.setOnAction(event -> loadContent("/lk/cmjd/ManageBranch.fxml"));

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (assignBranchTM manager : cbxManager.getItems()) {
                    if (manager.getUserID().equals(newValue.getUserID())) {
                        cbxManager.setValue(manager);
                    }
                }

                for (branchDto branch : cbxBranch.getItems()) {
                    if (branch.getBranchID().equals(newValue.getBranchID())) {
                        cbxBranch.setValue(branch);
                    }
                }
            }
        });

        btnAssign.setOnAction(event -> {
            try {
                assignService.assign(cbxManager.getValue().getUserID(), cbxBranch.getValue().getBranchID());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Successfully Assigned!");
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

        btnUnassign.setOnAction(event -> {
            try {
                assignService.assign(cbxManager.getValue().getUserID(), null);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Successfully Unassigned!");
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
    }

    public void TableSetup() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colBranchID.setCellValueFactory(new PropertyValueFactory<>("branchID"));

        try {
            ArrayList<assignBranchDto> dtos = assignService.getAll();

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

    private void loadManagers() {
        cbxManager.setItems(obList);
        cbxManager.setVisibleRowCount(4);

        cbxManager.setConverter(new StringConverter<assignBranchTM>() {

            @Override
            public assignBranchTM fromString(String arg0) {
                return null;
            }

            @Override
            public String toString(assignBranchTM manager) {
                return manager == null ? "" : manager.getUserID() + "(" + manager.getName() + ")";
            }

        });
    }

    private void loadBranches() {
        try {
            ArrayList<branchDto> branches = branchService.getAll();
            ObservableList<branchDto> obBranchList = FXCollections.observableArrayList(branches);

            cbxBranch.setItems(obBranchList);
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
        cbxBranch.setValue(null);
        cbxManager.setValue(null);
    }

}
