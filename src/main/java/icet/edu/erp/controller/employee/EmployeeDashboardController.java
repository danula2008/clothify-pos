package icet.edu.erp.controller.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EmployeeDashboardController {

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colHireDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        //TODO
    }

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
