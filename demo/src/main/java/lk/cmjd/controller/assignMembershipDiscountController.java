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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
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
    private ComboBox<membershipDiscountDto> cbxMembership;

    private membershipDiscountService service = (membershipDiscountService) serviceFactory.getInstance()
            .getService(serviceType.MEMBERSHIP_DISCOUNT);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TableSetup();
        loadMemberships();

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtDiscount.setText(String.valueOf(newValue.getDiscount()));

                for (membershipDiscountDto membership : cbxMembership.getItems()) {
                    if (membership.getTierId().equals(newValue.getTierId())) {
                        cbxMembership.setValue(membership);
                    }
                }
            }
        });

        btnClear.setOnAction(event -> clearForm());

        btnAssign.setOnAction(event -> {
            if (txtDiscount.getText().isEmpty() || cbxMembership.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please Enter Both Membership and Discount");
                alert.showAndWait();
            } else {
                try {
                    float dis = Float.parseFloat(txtDiscount.getText());
                    if (dis < 100 && dis > 0) {
                        service.assign(cbxMembership.getValue().getTiername(), dis);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setContentText("Successfully Assigned!");
                        alert.show();
                        TableSetup();
                        clearForm();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid Discount Amount");
                        alert.show();
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
                oblist.add(new membershipDiscountTM(t.getTierId(), t.getTiername(), t.getDiscount()));
            }

            tblData.setItems(oblist);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private void loadMemberships() {
        ArrayList<membershipDiscountDto> memberships;
        try {
            memberships = service.getAll();
            ObservableList<membershipDiscountDto> oblist = FXCollections.observableArrayList(memberships);

            cbxMembership.setItems(oblist);

            cbxMembership.setConverter(new StringConverter<membershipDiscountDto>() {

                @Override
                public membershipDiscountDto fromString(String arg0) {
                    return null;
                }

                @Override
                public String toString(membershipDiscountDto membership) {
                    return membership == null ? "" : membership.getTiername();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearForm() {
        cbxMembership.setValue(null);
        txtDiscount.setText("");
    }

}
