package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.service.serviceUtils;

public class mainController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private AnchorPane ancrSideBar;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMngBranch;

    @FXML
    private Button btnMngMembership;

    @FXML
    private Button btnMngCategory;

    @FXML
    private Button btnMngEquipment;

    @FXML
    private Button btnMngCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private Label txtUser;

    private String role;
    private String branch;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnLogout.setOnAction(event -> serviceUtils.changeScene(event, "/lk/cmjd/login.fxml", "Login", null,
                null, null, null));

        btnMngBranch.setOnAction(event -> loadContent("/lk/cmjd/ManageBranch.fxml"));

        btnMngMembership.setOnAction(event -> loadContent("/lk/cmjd/AssignMembershipDiscount.fxml"));

        btnMngCategory.setOnAction(event -> loadContent("/lk/cmjd/ManageCategory.fxml"));

        btnMngEquipment.setOnAction(event -> loadContent("/lk/cmjd/ManageEquipment.fxml"));

        btnMngCustomer.setOnAction(event -> loadContent("/lk/cmjd/ManageCustomer.fxml"));
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

    public void setUserInformation(String username, String role, String branch) {
        this.role = role;
        this.branch = branch;
        txtUser.setText(username + " (" + role + ")");
        setUpMenu();
    }

    public void setUpMenu() {
        btnMngBranch.setVisible(false);
        btnMngBranch.setManaged(false);

        btnMngMembership.setVisible(false);
        btnMngMembership.setManaged(false);

        if (role.equalsIgnoreCase("Admin")) {
            btnMngBranch.setVisible(true);
            btnMngBranch.setManaged(true);

            btnMngMembership.setVisible(true);
            btnMngMembership.setManaged(true);
        }
    }

}
