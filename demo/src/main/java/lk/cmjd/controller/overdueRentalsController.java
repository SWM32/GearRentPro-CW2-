package lk.cmjd.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.cmjd.dto.overdueDto;
import lk.cmjd.dto.tm.overdueTM;
import lk.cmjd.service.custom.overdueRentalsService;
import lk.cmjd.service.serviceFactory;
import lk.cmjd.service.serviceFactory.serviceType;
import lk.cmjd.util.sessionUtil;

public class overdueRentalsController implements Initializable {

    @FXML
    private AnchorPane ancrDisplay;

    @FXML
    private Button btnMngRentals;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblCID;

    @FXML
    private TableColumn<overdueTM, String> colBID;

    @FXML
    private TableColumn<overdueTM, String> colCID;

    @FXML
    private TableColumn<overdueTM, String> colContact;

    @FXML
    private TableColumn<overdueTM, LocalDate> colDue;

    @FXML
    private TableColumn<overdueTM, String> colEID;

    @FXML
    private TableColumn<overdueTM, String> colEmail;

    @FXML
    private TableColumn<overdueTM, Long> colOverdueDays;

    @FXML
    private TableView<overdueTM> tblData;

    @FXML
    private TextField txtSearchbar;

    private overdueRentalsService overdueService = (overdueRentalsService) serviceFactory.getInstance()
            .getService(serviceType.OVERDUE_RENTALS);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        overdueTableSetup();

        tblData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblCID.setText(newValue.getCusID());
                lblContact.setText(newValue.getContact());
                lblEmail.setText(newValue.getEmail());
            }
        });

        btnMngRentals.setOnAction((event) -> loadContent("/lk/cmjd/ManageRental.fxml"));
    }

    public void overdueTableSetup() {
        colCID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        colBID.setCellValueFactory(new PropertyValueFactory<>("branchID"));
        colEID.setCellValueFactory(new PropertyValueFactory<>("eqID"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("due"));
        colOverdueDays.setCellValueFactory(new PropertyValueFactory<>("dueDates"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            ArrayList<overdueDto> dtos = overdueService.getAll();

            String sessionBranch = sessionUtil.getSession().getBranch();

            ObservableList<overdueTM> obList = FXCollections.observableArrayList();
            if (sessionBranch != null) {
                for (overdueDto dto : dtos) {
                    if (dto.getBranchID().equals(sessionBranch)) {
                        obList.add(new overdueTM(dto.getCusID(), dto.getBranchID(), dto.getEqID(),
                                dto.getDue(), dto.getDueDates(), dto.getContact(), dto.getEmail()));
                    }
                }
            } else {
                for (overdueDto dto : dtos) {
                    obList.add(new overdueTM(dto.getCusID(), dto.getBranchID(), dto.getEqID(),
                            dto.getDue(), dto.getDueDates(), dto.getContact(), dto.getEmail()));
                }
            }

            // Wrap the ObservableList in a FilteredList (initially display all data)
            FilteredList<overdueTM> filteredData = new FilteredList<>(obList, b -> true);

            txtSearchbar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(overdue -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (overdue.getCusID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (overdue.getBranchID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (overdue.getEqID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (overdue.getContact().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (overdue.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(overdue.getDueDates()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (overdue.getDue().toString().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<overdueTM> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tblData.comparatorProperty());

            tblData.setItems(sortedData);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
}
