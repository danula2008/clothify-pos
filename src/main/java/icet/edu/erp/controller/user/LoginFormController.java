package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginFormController {

    @FXML
    private JFXButton btnPasswordImgToggle;

    @FXML
    private ImageView imgPasswordToogle;

    @FXML
    private JFXPasswordField pwdPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void lblForgotPasswordOnClick(MouseEvent event) {
        //TODO
    }

}
