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
import lk.cmjd.dto.branchDto;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.tm.manageEquipmentTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.itemCategoryService;
import lk.cmjd.service.custom.manageBranchService;
import lk.cmjd.service.custom.manageEquipmentService;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.util.sessionUtil;

public class manageEquipmentController implements Initializable {

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
    private ComboBox<branchDto> cbxBranch;

    @FXML
    private ComboBox<itemCategoryDto> cbxCategory;

    @FXML
    private ComboBox<String> cbxStatus;

    @FXML
    private TableColumn<manageEquipmentTM, Float> colBDP;

    @FXML
    private TableColumn<manageEquipmentTM, String> colBID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colBrand;

    @FXML
    private TableColumn<manageEquipmentTM, String> colCID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colEID;

    @FXML
    private TableColumn<manageEquipmentTM, String> colModel;

    @FXML
    private TableColumn<manageEquipmentTM, Float> colSDA;

    @FXML
    private TableColumn<manageEquipmentTM, String> colStatus;

    @FXML
    private TableColumn<manageEquipmentTM, Integer> colYear;

    @FXML
    private TableView<manageEquipmentTM> tblData;

    @FXML
    private TextField txtBDP;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtEID;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtSDA;

    @FXML
    private TextField txtSearchbar;

    @FXML
    private TextField txtYear;

    private manageEquipmentService equipmentService = (manageEquipmentService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_EQUIPMENT);

    private manageBranchService branchService = (manageBranchService) serviceFactory.getInstance()
            .getService(serviceType.MANAGE_BRANCH);

    private itemCategoryService categoryService = (itemCategoryService) serviceFactory.getInstance()
            .getService(serviceType.ITEM_CATEGORY);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();
        loadBranches();
        loadCategories();

        cbxStatus.getItems().addAll("AVAILABLE", "RESERVED", "RENTED", "MAINTENANCE");
        cbxStatus.setVisibleRowCount(3);

        btnAdd.setOnAction(event -> {
            if (cbxBranch.getValue() == null || cbxCategory.getValue() == null || cbxStatus.getValue() == null
                    || txtEID.getText().isBlank() || txtBrand.getText().isBlank() || txtModel.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please Fill the Essential Details");
                alert.show();
                return;
            }

            String eid = txtEID.getText();
            String bid = cbxBranch.getValue().getBranchID();
            String cid = cbxCategory.getValue().getCategoryID();
            String brand = txtBrand.getText();
            String model = txtModel.getText();
            int year = Integer.parseInt(txtYear.getText());
            float bdp = Float.parseFloat(txtBDP.getText());
            float sda = Float.parseFloat(txtSDA.getText());
            String status = cbxStatus.getValue();

            equipmentDto dto = new equipmentDto(eid, bid, cid, brand, model, year, bdp, sda, status);

            try {
                equipmentService.save(dto);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("New Equipment Added Successfully!");
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
            String eid = txtEID.getText();
            String bid = cbxBranch.getValue().getBranchID();
            String cid = cbxCategory.getValue().getCategoryID();
            String brand = txtBrand.getText();
            String model = txtModel.getText();
            int year = Integer.parseInt(txtYear.getText());
            float bdp = Float.parseFloat(txtBDP.getText());
            float sda = Float.parseFloat(txtSDA.getText());
            String status = cbxStatus.getValue();

            equipmentDto dto = new equipmentDto(eid, bid, cid, brand, model, year, bdp, sda, status);

            try {
                boolean success = equipmentService.update(dto);
                if (!success) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Equipment Could not be updated");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Equipment Updated Successfully!");
                alert.show();

                TableSetup();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnAction((event) -> {
            if (txtEID.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Equipment ID");
                alert.show();
                return;
            }
            try {
                boolean success = equipmentService.delete(txtEID.getText());
                if (success) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Equipment Deleted Successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText("Equipment Could not be deleted");
                    alert.show();
                }
                TableSetup();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnClear.setOnAction((event) -> clearForm());

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtEID.setText(newValue.getEquipment_id());
                txtBrand.setText(newValue.getBrand());
                txtModel.setText(newValue.getModel());
                txtYear.setText(String.valueOf(newValue.getYear()));
                txtBDP.setText(String.valueOf(newValue.getBdp()));
                txtSDA.setText(String.valueOf(newValue.getSda()));

                // Set ComboBox values based on the selected equipment
                for (branchDto branch : cbxBranch.getItems()) {
                    if (branch.getBranchID().equals(newValue.getBranch_id())) {
                        cbxBranch.setValue(branch);
                        break;
                    }
                }

                for (itemCategoryDto category : cbxCategory.getItems()) {
                    if (category.getCategoryID().equals(newValue.getCategory_id())) {
                        cbxCategory.setValue(category);
                        break;
                    }
                }

                cbxStatus.setValue(newValue.getStatus());
            }
        });
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

            // setup toString method to display names in the comboBox
            cbxBranch.setConverter(new StringConverter<branchDto>() {

                // Runs if the user types something into the box to convert that text back into
                // a branchDto object
                // Since this object is not editable we just return null
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

    private void loadCategories() {
        try {
            ArrayList<itemCategoryDto> categories = categoryService.getAll();
            ObservableList<itemCategoryDto> obList = FXCollections.observableArrayList(categories);

            cbxCategory.setItems(obList);
            cbxCategory.setVisibleRowCount(4);

            cbxCategory.setConverter(new StringConverter<itemCategoryDto>() {

                @Override
                public itemCategoryDto fromString(String string) {
                    return null;
                }

                @Override
                public String toString(itemCategoryDto category) {
                    return category == null ? "" : category.getCategoryID() + "(" + category.getName() + ")";
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void TableSetup() {
        colEID.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
        colBID.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colBDP.setCellValueFactory(new PropertyValueFactory<>("bdp"));
        colSDA.setCellValueFactory(new PropertyValueFactory<>("sda"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            ArrayList<equipmentDto> dtos = equipmentService.getAll();

            String sessionBranch = sessionUtil.getSession().getBranch();

            ObservableList<manageEquipmentTM> obList = FXCollections.observableArrayList();
            if (sessionBranch != null) {
                for (equipmentDto dto : dtos) {
                    if (dto.getBranch_id().equals(sessionBranch)) {
                        obList.add(
                                new manageEquipmentTM(dto.getEquipment_id(), dto.getBranch_id(), dto.getCategory_id(),
                                        dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(), dto.getSda(),
                                        dto.getStatus()));
                    }
                }
            } else {
                for (equipmentDto dto : dtos) {
                    obList.add(new manageEquipmentTM(dto.getEquipment_id(), dto.getBranch_id(), dto.getCategory_id(),
                            dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(), dto.getSda(),
                            dto.getStatus()));
                }
            }

            // Wrap the ObservableList in a FilteredList (initially display all data)
            FilteredList<manageEquipmentTM> filteredData = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
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

            // Wrap the FilteredList in a SortedList.
            // Otherwise, sorting the table by clicking headers won't work.
            SortedList<manageEquipmentTM> sortedData = new SortedList<>(filteredData);

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
        txtEID.setText("");
        txtBrand.setText("");
        txtModel.setText("");
        txtYear.setText("");
        txtBDP.setText("");
        txtSDA.setText("");
        if (sessionUtil.getSession().getBranch() == null) {
            cbxBranch.setValue(null);
        }
        cbxCategory.setValue(null);
        cbxStatus.setValue(null);
    }
}
