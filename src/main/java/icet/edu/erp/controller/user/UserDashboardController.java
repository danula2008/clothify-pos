package icet.edu.erp.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UserDashboardController {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colInitialLogin;

    @FXML
    private TableColumn<?, ?> colLastLogin;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<?> tblUsers;

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
