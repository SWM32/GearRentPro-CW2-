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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.tm.itemCategoryTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.itemCategoryService;
import lk.cmjd.service.serviceFactory.serviceType;

public class manageCategoryController implements Initializable {

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
    private TableColumn<itemCategoryTM, Float> colBPF;

    @FXML
    private TableColumn<itemCategoryTM, String> colDescription;

    @FXML
    private TableColumn<itemCategoryTM, String> colID;

    @FXML
    private TableColumn<itemCategoryTM, Float> colLFPD;

    @FXML
    private TableColumn<itemCategoryTM, Float> colLRDR;

    @FXML
    private TableColumn<itemCategoryTM, String> colName;

    @FXML
    private TableColumn<itemCategoryTM, Float> colWM;

    @FXML
    private TableView<itemCategoryTM> tblData;

    @FXML
    private TextField txtBPF;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLFPD;

    @FXML
    private TextField txtLRDR;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearchbar;

    @FXML
    private TextField txtWM;

    private itemCategoryService service = (itemCategoryService) serviceFactory.getInstance()
            .getService(serviceType.ITEM_CATEGORY);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();

        btnAdd.setOnAction((event) -> {
            String id = txtID.getText();
            String name = txtName.getText();
            String description = txtDescription.getText();
            float bpf = Float.parseFloat(txtBPF.getText());
            float wm = Float.parseFloat(txtWM.getText());
            float lrdr = Float.parseFloat(txtLRDR.getText());
            float lfpd = Float.parseFloat(txtLFPD.getText());

            itemCategoryDto dto = new itemCategoryDto(id, name, description, bpf, wm, lrdr, lfpd);

            try {
                service.save(dto);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("New Category Added Successfully!");
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
                itemCategoryDto dto = service.search(txtID.getText());
                if (dto == null) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Category Not Found");
                    alert.showAndWait();
                    return;
                }
                txtName.setText(dto.getName());
                txtDescription.setText(dto.getDescription());
                txtBPF.setText(String.valueOf(dto.getBPF()));
                txtWM.setText(String.valueOf(dto.getWM()));
                txtLRDR.setText(String.valueOf(dto.getLRDR()));
                txtLFPD.setText(String.valueOf(dto.getLFPD()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnUpdate.setOnAction((event) -> {
            String id = txtID.getText();
            String name = txtName.getText();
            String description = txtDescription.getText();
            float bpf = Float.parseFloat(txtBPF.getText());
            float wm = Float.parseFloat(txtWM.getText());
            float lrdr = Float.parseFloat(txtLRDR.getText());
            float lfpd = Float.parseFloat(txtLFPD.getText());

            itemCategoryDto dto = new itemCategoryDto(id, name, description, bpf, wm, lrdr, lfpd);

            try {
                boolean success = service.update(dto);
                if (!success) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Category Could not be updated");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Category Updated Successfully!");
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
                alert.setContentText("Please Enter Category ID");
                alert.show();
                return;
            }
            try {
                boolean success = service.delete(txtID.getText());
                if (success) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Category Deleted Successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText("Category Could not be deleted");
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
                txtID.setText(newValue.getCategoryID());
                txtName.setText(newValue.getName());
                txtDescription.setText(newValue.getDescription());
                txtBPF.setText(String.valueOf(newValue.getBPF()));
                txtWM.setText(String.valueOf(newValue.getWM()));
                txtLRDR.setText(String.valueOf(newValue.getLRDR()));
                txtLFPD.setText(String.valueOf(newValue.getLFPD()));
            }
        });
    }

    public void TableSetup() {
        colID.setCellValueFactory(new PropertyValueFactory<>("CategoryID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBPF.setCellValueFactory(new PropertyValueFactory<>("BPF"));
        colWM.setCellValueFactory(new PropertyValueFactory<>("WM"));
        colLRDR.setCellValueFactory(new PropertyValueFactory<>("LRDR"));
        colLFPD.setCellValueFactory(new PropertyValueFactory<>("LFPD"));

        try {
            ArrayList<itemCategoryDto> dtos = service.getAll();

            ObservableList<itemCategoryTM> obList = FXCollections.observableArrayList();
            for (itemCategoryDto dto : dtos) {
                obList.add(new itemCategoryTM(dto.getCategoryID(), dto.getName(), dto.getDescription(), dto.getBPF(),
                        dto.getWM(), dto.getLRDR(), dto.getLFPD()));
            }

            // Wrap the ObservableList in a FilteredList (initially display all data)
            FilteredList<itemCategoryTM> filteredData = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(category -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (category.getCategoryID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (category.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (category.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            // Wrap the FilteredList in a SortedList.
            // Otherwise, sorting the table by clicking headers won't work.
            SortedList<itemCategoryTM> sortedData = new SortedList<>(filteredData);

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
        txtID.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtBPF.setText("");
        txtWM.setText("");
        txtLRDR.setText("");
        txtLFPD.setText("");
    }

}
