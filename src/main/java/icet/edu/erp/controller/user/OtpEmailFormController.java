package icet.edu.erp.controller.user;

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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class OtpEmailFormController {

    @FXML
    private Label lblInvalidEmail;

    @FXML
    private JFXTextField txtEmailAddress;

    private final UserService service =  ServiceFactory.getInstance().getServiceType(ServiceType.USER);

    @FXML
    void btnSendOtpOnAction(ActionEvent event) {
        if(
                Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", txtEmailAddress.getText()) &&
                service.isEmailInSystem(txtEmailAddress.getText())
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/otp_form.fxml"));
                Scene scene = new Scene(loader.load());
                OtpFormController controller = loader.getController();
                controller.setOTP(service.sendOTP(txtEmailAddress.getText().toLowerCase()));

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                ShowAlert.fileNotFoundError();
            }
            Stage stage = (Stage) txtEmailAddress.getScene().getWindow();
            stage.close();
            return;
        }
        lblInvalidEmail.setVisible(true);
    }
}
