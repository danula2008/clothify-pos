package icet.edu.erp.controller.order;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class OrderDashboardController {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLastOrderDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPendingPayment;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<?> tblSupplier;

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
    void btnViewProductsOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        //TODO
    }

}
