package icet.edu.erp.controller.customer;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CustomerDashboardController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private Group btnEditBtnDelete;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJoinDate;

    @FXML
    private TableColumn<?, ?> colLoyaltyTier;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private ImageView imgDelete;

    @FXML
    private ImageView imgEdit;

    @FXML
    private TableView<?> tblCustomers;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        //TODO
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        //TODO
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        //TODO
    }

}
