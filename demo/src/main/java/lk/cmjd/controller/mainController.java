package lk.cmjd.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.cmjd.service.serviceUtils;

public class mainController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label txtUser;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnLogout.setOnAction((event) -> serviceUtils.changeScene(event, "/lk/cmjd/login.fxml", "Login", null,
                null, null));
    }

    public void setUserInformation(String username, String role) {
        txtUser.setText(username + " (" + role + ")");
    }

}
