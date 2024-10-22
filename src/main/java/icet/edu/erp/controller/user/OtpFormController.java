package icet.edu.erp.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.util.ShowAlert;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class OtpFormController implements Initializable {

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

    private String otp;

    @FXML
    void btnDoneOnAction(ActionEvent event){
        String enteredOTP = txtOtpChar1.getText() +
                txtOtpChar2.getText() +
                txtOtpChar3.getText() +
                txtOtpChar4.getText() +
                txtOtpChar5.getText() +
                txtOtpChar6.getText();

        if (enteredOTP.length()!=6){
            return;
        }

        if (otp.equals(enteredOTP)
        ){
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user/change_password_form.fxml"))));
                stage.show();
            } catch (IOException e) {
                ShowAlert.fileNotFoundError();
            }
        } else{
            ShowAlert.customAlert("Invalid OTP", "OTP entered is invalid.", Alert.AlertType.ERROR);
        }
        Stage stage = (Stage) txtOtpChar1.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startCountDown();
        otpInputFormatter();
    }

    private void startCountDown(){
        AtomicInteger seconds = new AtomicInteger(300); // 5 minutes countdown
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            if (seconds.get() > 0) {
                seconds.decrementAndGet(); // Decrement the seconds
                lblCountdown.setText(String.format("%02d:%02d", seconds.get() / 60, seconds.get() % 60));
            } else {
                timeline.stop();
                lblCountdown.setText("Countdown finished!");
            }
        }));
        timeline.play();
    }

    private void otpInputFormatter(){
        JFXTextField[] labels = new JFXTextField[] {txtOtpChar1, txtOtpChar2, txtOtpChar3, txtOtpChar4, txtOtpChar5, txtOtpChar6};

        for (int i = 0; i < labels.length; i++) {
            final int index = i;

            labels[index].setTextFormatter(new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                return (newText.length() <= 1 && newText.matches("\\d?")) ? change : null;
            }));

            labels[index].textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() == 1) {
                    if (index < labels.length - 1) {
                        labels[index + 1].requestFocus();
                    }
                } else if (newValue.isEmpty() && oldValue.length() == 1 && index > 0) {
                        labels[index - 1].requestFocus();
                    }

            });

            labels[index].setOnKeyPressed(event -> {
                if (event.getCode() == javafx.scene.input.KeyCode.BACK_SPACE && index > 0) {
                    labels[index - 1].requestFocus();
                }
            });
        }
    }

    void setOTP(String otp){
        this.otp = otp;
    }
}
