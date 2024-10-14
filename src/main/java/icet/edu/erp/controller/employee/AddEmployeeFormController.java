package icet.edu.erp.controller.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AddEmployeeFormController {

    @FXML
    private JFXButton btnCheckEmail;

    @FXML
    private JFXButton btnDone;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private DatePicker dateBDay;

    @FXML
    private DatePicker dateHireDate;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    void btnCheckEmailOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        //TODO
    }

}
