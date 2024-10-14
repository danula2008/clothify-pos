package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OtpFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private Label lblCountdown;

    @FXML
    private Label lblEmailAddress;

    @FXML
    private Label lblInvalidOtp;

    @FXML
    private JFXTextField txtOtpChar1;

    @FXML
    private JFXTextField txtOtpChar2;

    @FXML
    private JFXTextField txtOtpChar3;

    @FXML
    private JFXTextField txtOtpChar4;

    @FXML
    private JFXTextField txtOtpChar5;

    @FXML
    private JFXTextField txtOtpChar6;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        //TODO
    }

}
