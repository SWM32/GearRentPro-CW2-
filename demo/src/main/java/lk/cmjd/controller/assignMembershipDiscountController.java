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
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.dto.tm.membershipDiscountTM;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.custom.membershipDiscountService;
import lk.cmjd.service.serviceFactory.serviceType;

public class assignMembershipDiscountController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<membershipDiscountTM, Float> colDiscount;

    @FXML
    private TableColumn<membershipDiscountTM, String> colTierName;

    @FXML
    private TableView<membershipDiscountTM> tblData;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtTierName;

    private membershipDiscountService service = (membershipDiscountService) serviceFactory.getInstance()
            .getService(serviceType.MEMBERSHIP_DISCOUNT);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtTierName.setText(newValue.getTiername());
                txtDiscount.setText(String.valueOf(newValue.getDiscount()));
            }
        });

        btnClear.setOnAction(event -> clearForm());

        btnAssign.setOnAction(event -> {
            if (txtTierName.getText().isEmpty() || txtDiscount.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Both TierName and Discount");
                alert.showAndWait();
            } else {
                try {
                    String name = txtTierName.getText().toUpperCase();
                    if (name.equals("REGULAR") || name.equals("SILVER") || name.equals("GOLD")) {
                        float dis = Float.parseFloat(txtDiscount.getText());
                        if (dis < 100 && dis > 0) {
                            service.assign(txtTierName.getText(), dis);
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success!");
                            alert.setContentText("Successfully Assigned!");
                            alert.show();
                            TableSetup();
                            clearForm();
                        } else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Fail");
                            alert.setContentText("Invalid Discount Rate");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Fail");
                        alert.setContentText("Invalid Tier Name");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Fail");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });

    }

    public void TableSetup() {
        colTierName.setCellValueFactory(new PropertyValueFactory<>("tiername"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            ArrayList<membershipDiscountDto> dtos = service.getAll();

            ObservableList<membershipDiscountTM> oblist = FXCollections.observableArrayList();

            for (membershipDiscountDto t : dtos) {
                oblist.add(new membershipDiscountTM(t.getTiername(), t.getDiscount()));
            }

            tblData.setItems(oblist);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private void clearForm() {
        txtTierName.setText("");
        txtDiscount.setText("");
    }

}
