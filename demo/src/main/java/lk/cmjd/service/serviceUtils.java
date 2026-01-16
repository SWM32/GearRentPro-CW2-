package lk.cmjd.service;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.cmjd.controller.mainController;

public class serviceUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String userId, String username,
            String role, String branch) {
        Parent root = null;

        if (userId != null) {
            try {
                FXMLLoader loader = new FXMLLoader(serviceUtils.class.getResource(fxmlFile));
                root = loader.load();
                mainController mainController = loader.getController();
                mainController.setUserInformation(username, role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(serviceUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        if (title.equals("Login") || title.equals("SignUp")) {
            stage.setScene(new Scene(root, 600, 400));
        } else {
            stage.setScene(new Scene(root, 850, 500));
        }
        stage.show();
    }
}
