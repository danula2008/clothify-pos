package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.User;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.UserService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

public class ChangePasswordFormController {

    @FXML
    private ImageView imgCfmNewPwdViewToggle;

    @FXML
    private ImageView imgNewPwdViewToggle;

    @FXML
    private ImageView imgOldPwdViewToggle;


    @FXML
    private Label lblErrorMsg;

    @FXML
    private JFXPasswordField pwdCfmNewPwd;

    @FXML
    private JFXPasswordField pwdNewPwd;

    @FXML
    private JFXPasswordField pwdOldPwd;

    @FXML
    private JFXTextField txtCfmNewPwd;

    @FXML
    private JFXTextField txtNewPwd;

    @FXML
    private JFXTextField txtOldPwd;

    private boolean showPassword = false;

    private boolean showOldPassword = false;

    private boolean showConfirmPassword = false;
    private User user;

    private String showPwdImg = "/img/show_password.png";
    private String hidePwdImg = "/img/hide_password.png";

    @FXML
    void btnCfmNewPwdViewToggleOnAction(ActionEvent event) {
        if (showConfirmPassword){
            pwdCfmNewPwd.setText(txtCfmNewPwd.getText());
            txtCfmNewPwd.setVisible(false);
            pwdCfmNewPwd.setVisible(true);
            imgCfmNewPwdViewToggle.setImage(new Image(showPwdImg));
            showConfirmPassword = false;
            return;
        }
        txtCfmNewPwd.setText(pwdCfmNewPwd.getText());
        pwdCfmNewPwd.setVisible(false);
        txtCfmNewPwd.setVisible(true);
        imgCfmNewPwdViewToggle.setImage(new Image(hidePwdImg));
        showConfirmPassword = true;
    }

    private boolean crossValPwd() {
        String oldPassword = showOldPassword? txtOldPwd.getText() : pwdOldPwd.getText();
        String passwordsMatch = showPassword ? txtNewPwd.getText() : pwdNewPwd.getText();
        String cfmPassword = showConfirmPassword ? txtCfmNewPwd.getText() : pwdCfmNewPwd.getText();

        if (!passwordsMatch.equals(cfmPassword)) {
            lblErrorMsg.setText("New Passwords do not match.");
            return false;
        }
        if (!oldPassword.equals(user.getPassword())) {
            lblErrorMsg.setText("Old Passwords do not match.");
            return false;
        }
        return true;
    }

    @FXML
    void btnChangePwdOnAction(ActionEvent event) {
        if (crossValPwd()){
            return;
        }

        if (((UserService) ServiceFactory.getInstance().getServiceType(ServiceType.USER)).updatePassword(user.getId(), showPassword? txtNewPwd.getText() : pwdNewPwd.getText())) {
            ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/main_dashboard.fxml"))));
                stage.setResizable(false);
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                ShowAlert.fileNotFoundError();
            }
            Stage stage = (Stage) txtOldPwd.getScene().getWindow();
            stage.close();
        } else {
            ShowAlert.customAlert("Error", "Could not update the Database.\nPlease reload the table.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnNewPwdViewToggleOnAction(ActionEvent event) {
        if (showPassword){
            pwdNewPwd.setText(txtNewPwd.getText());
            txtNewPwd.setVisible(false);
            pwdNewPwd.setVisible(true);
            imgNewPwdViewToggle.setImage(new Image(showPwdImg));
            showPassword = false;
            return;
        }
        txtNewPwd.setText(pwdNewPwd.getText());
        pwdNewPwd.setVisible(false);
        txtNewPwd.setVisible(true);
        imgNewPwdViewToggle.setImage(new Image(hidePwdImg));
        showPassword = true;
    }

    @FXML
    void btnOldPwdViewToggleOnAction(ActionEvent event) {
        if (showOldPassword){
            pwdOldPwd.setText(txtOldPwd.getText());
            txtOldPwd.setVisible(false);
            pwdOldPwd.setVisible(true);
            imgOldPwdViewToggle.setImage(new Image(showPwdImg));
            showOldPassword = false;
            return;
        }
        txtOldPwd.setText(pwdOldPwd.getText());
        pwdOldPwd.setVisible(false);
        txtOldPwd.setVisible(true);
        imgOldPwdViewToggle.setImage(new Image(hidePwdImg));
        showOldPassword = true;
    }

    public void setUser(User user){
        this.user = user;
    }

}
