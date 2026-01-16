package lk.cmjd.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

            tblData.setItems(obList);
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
        txtAddress.setText("");
        txtContact.setText("");
    }

}
