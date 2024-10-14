package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddUserFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private JFXComboBox<?> cmbRole;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXPasswordField pwdCfmPassword;

    @FXML
    private JFXPasswordField pwdPassword;

    @FXML
    private JFXTextField txtCfmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        //TODO
    }

}
