package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.service.serviceUtils;
import lk.cmjd.util.sessionUtil;

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
    private Button btnMngReservation;

    @FXML
    private Button btnLogout;

    @FXML
    private Label txtUser;

    private String role;
    private String branch;

    private List<Button> menuButtons;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        menuButtons = new ArrayList<>(Arrays.asList(btnHome, btnMngBranch, btnMngMembership, btnMngCategory,
                btnMngEquipment, btnMngCustomer, btnMngReservation));

        btnLogout.setOnAction(
                event -> {
                    serviceUtils.changeScene(event, "/lk/cmjd/login.fxml", "Login", null, null, null, null);
                    sessionUtil.getSession().clearSession();
                });

        btnHome.setOnAction(event -> {
            updateActiveState(btnHome);
            ancrDisplay.getChildren().clear();
        });

        btnMngBranch.setOnAction(event -> {
            updateActiveState(btnMngBranch);
            loadContent("/lk/cmjd/ManageBranch.fxml");
        });

        btnMngMembership.setOnAction(event -> {
            updateActiveState(btnMngMembership);
            loadContent("/lk/cmjd/AssignMembershipDiscount.fxml");
        });

        btnMngCategory.setOnAction(event -> {
            updateActiveState(btnMngCategory);
            loadContent("/lk/cmjd/ManageCategory.fxml");
        });

        btnMngEquipment.setOnAction(event -> {
            updateActiveState(btnMngEquipment);
            loadContent("/lk/cmjd/ManageEquipment.fxml");
        });

        btnMngCustomer.setOnAction(event -> {
            updateActiveState(btnMngCustomer);
            loadContent("/lk/cmjd/ManageCustomer.fxml");
        });

        btnMngReservation.setOnAction(event -> {
            updateActiveState(btnMngReservation);
            loadContent("/lk/cmjd/ManageReservation.fxml");
        });

        updateActiveState(btnHome);
    }

    private void updateActiveState(Button activeBtn) {
        for (Button btn : menuButtons) {
            btn.getStyleClass().remove("active");
        }
        activeBtn.getStyleClass().add("active");
    }

    private void loadContent(String file, String... branch) {
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
        sessionUtil.getSession().setBranch(branch);
        sessionUtil.getSession().setRole(role);
        setUpMenu();
    }

    public void setUpMenu() {
        btnMngBranch.setVisible(false);
        btnMngBranch.setManaged(false);

        btnMngMembership.setVisible(false);
        btnMngMembership.setManaged(false);

        btnMngCategory.setVisible(false);
        btnMngCategory.setManaged(false);

        btnMngCustomer.setVisible(false);
        btnMngCustomer.setManaged(false);

        btnMngEquipment.setVisible(false);
        btnMngEquipment.setManaged(false);

        btnMngReservation.setVisible(false);
        btnMngReservation.setManaged(false);

        if (role.equalsIgnoreCase("Admin")) {
            btnMngBranch.setVisible(true);
            btnMngBranch.setManaged(true);

            btnMngMembership.setVisible(true);
            btnMngMembership.setManaged(true);

            btnMngCategory.setVisible(true);
            btnMngCategory.setManaged(true);

            btnMngCustomer.setVisible(true);
            btnMngCustomer.setManaged(true);

            btnMngEquipment.setVisible(true);
            btnMngEquipment.setManaged(true);
        }

        if (role.equalsIgnoreCase("Manager")) {
            btnMngCustomer.setVisible(true);
            btnMngCustomer.setManaged(true);

            if (branch != null) {
                btnMngEquipment.setVisible(true);
                btnMngEquipment.setManaged(true);

                btnMngReservation.setVisible(true);
                btnMngReservation.setManaged(true);
            }

            btnMngCategory.setVisible(true);
            btnMngCategory.setManaged(true);
        }

        if (role.equalsIgnoreCase("Staff")) {
            btnMngCustomer.setVisible(true);
            btnMngCustomer.setManaged(true);

            btnMngReservation.setVisible(true);
            btnMngReservation.setManaged(true);
        }
    }
}