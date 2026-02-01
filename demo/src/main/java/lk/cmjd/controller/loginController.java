package lk.cmjd.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.cmjd.dto.loginDto;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceUtils;
import lk.cmjd.service.custom.loginService;

public class loginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserID;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loginService loginService = (loginService) serviceFactory.getInstance()
                .getService(serviceFactory.serviceType.LOGIN);

        btnLogin.setOnAction((event) -> {
            if (!txtUserID.getText().trim().isEmpty() && !txtPassword.getText().trim().isEmpty()) {
                try {
                    loginDto dto = loginService.loginUser(txtUserID.getText());

                    if (dto != null) {
                        String storedHash = dto.getPassword();

                        if (BCrypt.checkpw(txtPassword.getText(), storedHash)) {
                            serviceUtils.changeScene(event, "/lk/cmjd/main.fxml", "GearRentPro", dto.getUserId(),
                                    dto.getUsername(), dto.getRole(), dto.getBranch());
                        } else {
                            System.out.println("Passwords did not match");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Provided credential are incorrect!");
                            alert.show();
                        }
                    } else {
                        System.out.println("User not Found!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credential are incorrect!");
                        alert.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            } else {
                System.out.println("Please fill in all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to sign up!");
                alert.show();
            }
        });

        btnSignUp.setOnAction((event) -> {
            serviceUtils.changeScene(event, "/lk/cmjd/signUp.fxml", "SignUp", null,
                    null, null, null);
        });
    }

}
