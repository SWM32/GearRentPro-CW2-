package lk.cmjd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class manageRentalController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cbxBranch;

    @FXML
    private ComboBox<?> cbxEquipment;

    @FXML
    private ComboBox<?> cbxPayStatus;

    @FXML
    private ComboBox<?> cbxRentStatus;

    @FXML
    private TableColumn<?, ?> colEqBDP;

    @FXML
    private TableColumn<?, ?> colEqBID;

    @FXML
    private TableColumn<?, ?> colEqBrand;

    @FXML
    private TableColumn<?, ?> colEqCID;

    @FXML
    private TableColumn<?, ?> colEqEID;

    @FXML
    private TableColumn<?, ?> colEqModel;

    @FXML
    private TableColumn<?, ?> colEqSDA;

    @FXML
    private TableColumn<?, ?> colEqStatus;

    @FXML
    private TableColumn<?, ?> colEqYear;

    @FXML
    private TableColumn<?, ?> colRenARD;

    @FXML
    private TableColumn<?, ?> colRenBID;

    @FXML
    private TableColumn<?, ?> colRenCusID;

    @FXML
    private TableColumn<?, ?> colRenDue;

    @FXML
    private TableColumn<?, ?> colRenEID;

    @FXML
    private TableColumn<?, ?> colRenFP;

    @FXML
    private TableColumn<?, ?> colRenLRD;

    @FXML
    private TableColumn<?, ?> colRenMD;

    @FXML
    private TableColumn<?, ?> colRenPS;

    @FXML
    private TableColumn<?, ?> colRenREID;

    @FXML
    private TableColumn<?, ?> colRenRS;

    @FXML
    private TableColumn<?, ?> colRenSDH;

    @FXML
    private TableColumn<?, ?> colRenStart;

    @FXML
    private TableColumn<?, ?> colRenTR;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private TableView<?> tblEquipmentData;

    @FXML
    private TableView<?> tblRentalData;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtEqSearchbar;

    @FXML
    private TextField txtREID;

    @FXML
    private TextField txtRenSearchbar;

}
