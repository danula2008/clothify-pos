package icet.edu.erp.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AddCustomerFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private DatePicker dateDob;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        //TODO
    }

}
