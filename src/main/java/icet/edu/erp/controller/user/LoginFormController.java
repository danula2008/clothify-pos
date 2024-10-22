package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.UserService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFormController {

    @FXML
    private ImageView imgPasswordToggle;

    @FXML
    private JFXPasswordField pwdPassword;

    @FXML
    private Label lblError;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    private boolean showPwd = false;

    @FXML
    void btnPasswordImgToggleOnAction(ActionEvent event) {
        if (showPwd){
            pwdPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            pwdPassword.setVisible(true);
            imgPasswordToggle.setImage(new Image("/img/show_password.png"));
            showPwd = false;
            return;
        }
        txtPassword.setText(pwdPassword.getText());
        pwdPassword.setVisible(false);
        txtPassword.setVisible(true);
        imgPasswordToggle.setImage(new Image("/img/hide_password.png"));
        showPwd = true;
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if (!validateInfo()){
            return;
        }
        if(((UserService)ServiceFactory.getInstance().getServiceType(ServiceType.USER)).validateLogin(txtEmail.getText(), txtPassword.getText())){
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/main_dashboard.fxml"))));
                stage.setResizable(false);
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                ShowAlert.fileNotFoundError();
            }
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.close();
        }
        lblError.setText("Invalid email or password.");
    }

    @FXML
    void lblForgotPasswordOnClick(MouseEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user/otp_email_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }

    private boolean validateInfo(){
        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", txtEmail.getText())){
            lblError.setText("Enter a valid email.");
            return false;
        }
        if(showPwd? txtPassword.getText().isEmpty() : pwdPassword.getText().isEmpty()){
            lblError.setText("Enter your password.");
            return false;
        }
        return true;
    }
}
