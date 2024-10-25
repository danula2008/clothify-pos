package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.User;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.UserService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UserDataFormController implements Initializable {

    @FXML
    public ImageView imgPwd;

    @FXML
    public ImageView imgConfirmPwd;

    @FXML
    private JFXButton btnDone;

    @FXML
    private JFXComboBox<String> cmbRole;

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

    private final UserService service = ServiceFactory.getInstance().getServiceType(ServiceType.USER);
    private boolean isAdd = true;
    private Integer id;

    private boolean showPassword = false;

    private boolean showConfirmPassword = false;



    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate() || crossValPwd()) {
            return;
        }

        try {
            if (service.addUser(new User(
                            isAdd ? null : id,
                            txtName.getText(),
                            txtEmail.getText(),
                            showPassword? txtPassword.getText() : pwdPassword.getText(),
                            cmbRole.getValue(),
                            Timestamp.valueOf(LocalDateTime.now()),
                            Timestamp.valueOf(LocalDateTime.now())
                    )
            )) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtName, txtEmail).forEach(JFXTextField::clear);
                    cmbRole.setValue("Cashier");
                } else {
                    Stage stage = (Stage) txtName.getScene().getWindow();
                    stage.close();
                }

            } else {
                ShowAlert.customAlert("Error", "Could not update the Database.\nPlease reload the table.", Alert.AlertType.ERROR);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            ShowAlert.customAlert("Item Code Error", "Enter a unique code for the item.", Alert.AlertType.ERROR);
        }
    }

    private boolean crossValPwd() {
        String passwordsMatch = showPassword ? txtPassword.getText() : pwdPassword.getText();
        String cfmPassword = showConfirmPassword ? txtCfmPassword.getText() : pwdCfmPassword.getText();

        if (!passwordsMatch.equals(cfmPassword)) {
            lblErrorMsg.setText("Passwords do not match.");
            return false;
        }
        return true;
    }

    @FXML
    void btnToggleCfrmPwdOnAction(ActionEvent event) {
        if (showConfirmPassword){
            pwdPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            pwdPassword.setVisible(true);
            imgConfirmPwd.setImage(new Image("/img/show_password.png"));
            showConfirmPassword = false;
            return;
        }
        txtPassword.setText(pwdPassword.getText());
        pwdPassword.setVisible(false);
        txtPassword.setVisible(true);
        imgConfirmPwd.setImage(new Image("/img/hide_password.png"));
        showConfirmPassword = true;
    }

    @FXML
    void btnTogglePwdOnAction(ActionEvent event) {
        if (showPassword){
            pwdPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            pwdPassword.setVisible(true);
            imgPwd.setImage(new Image("/img/show_password.png"));
            showPassword = false;
            return;
        }
        txtPassword.setText(pwdPassword.getText());
        pwdPassword.setVisible(false);
        txtPassword.setVisible(true);
        imgPwd.setImage(new Image("/img/hide_password.png"));
        showPassword = true;
    }

    public void setUser(User selectedData) {
        isAdd = false;
        id = selectedData.getId();
        lblTitle.setText("Update User");

        txtName.setText(selectedData.getName());
        txtEmail.setText(selectedData.getEmail());
        cmbRole.setValue(selectedData.getRole());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbRole.setItems(FXCollections.observableArrayList("Admin", "Cashier"));
    }

    private boolean validate() {
        if (txtName.getText().trim().isEmpty() || !txtName.getText().matches("^[A-Za-z\\s]+$")) {
            lblErrorMsg.setText("Name must not be empty and can only contain letters and spaces.");
            return false;
        }

        if (!txtEmail.getText().matches("^[\\w._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$")) {
            lblErrorMsg.setText("Email must be a valid email address.");
            return false;
        }

        return true;
    }
}
