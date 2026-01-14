package lk.cmjd.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import lk.cmjd.dto.signUpDto;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceUtils;
import lk.cmjd.service.custom.signUpService;

public class signUpController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private RadioButton rdoOp1;

    @FXML
    private RadioButton rdoOp2;

    @FXML
    private RadioButton rdoOp3;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtUsername;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        signUpService signUpService = (signUpService) serviceFactory.getInstance()
                .getService(serviceFactory.serviceType.SIGNUP);

        ToggleGroup toggleGroup = new ToggleGroup();
        rdoOp1.setToggleGroup(toggleGroup);
        rdoOp2.setToggleGroup(toggleGroup);
        rdoOp3.setToggleGroup(toggleGroup);

        rdoOp1.setSelected(true);

        btnSignUp.setOnAction((event) -> {
            String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

            if (!txtUserID.getText().trim().isEmpty() && !txtPassword.getText().trim().isEmpty()
                    && !txtUsername.getText().trim().isEmpty()) {
                signUpDto dto = new signUpDto(txtUserID.getText(), txtUsername.getText(), txtPassword.getText(),
                        toggleName);
                try {
                    if (signUpService.saveUser(dto)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("User Registered Successflully!");
                        alert.showAndWait();

                        serviceUtils.changeScene(event, "/lk/cmjd/main.fxml", "Welcome", txtUserID.getText(),
                                txtUsername.getText(), toggleName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else {
                System.out.println("Please fill in all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to sign up!");
                alert.show();
            }
        });

        btnLogin.setOnAction((event) -> serviceUtils.changeScene(event, "/lk/cmjd/login.fxml", "Login", null,
                null, null));

    }

}
