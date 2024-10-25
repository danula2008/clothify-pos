package icet.edu.erp.controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Customer;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.CustomerService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CustomerDataFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbGender;

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

    private final CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    private boolean isAdd = true;
    private Integer id;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate()) {
            return;
        }

        Customer customer = new Customer(
                isAdd ? null : id,
                txtName.getText(),
                cmbGender.getValue(),
                txtEmail.getText(),
                txtContact.getText(),
                dateDob.getValue(),
                isAdd ? LocalDate.now() : service.getDate(id),
                ""
        );

        try {
            if (isAdd? service.addCustomer(customer) : service.updateCustomer(customer)) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtName, txtEmail, txtContact).forEach(JFXTextField::clear);
                    cmbGender.setValue("Male");
                    dateDob.setValue(null);
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

    void setCustomer(Customer customer) {
        isAdd = false;
        lblTitle.setText("Update Customer");
        this.id = customer.getId();

        txtName.setText(customer.getName());
        cmbGender.setValue(customer.getGender());
        txtContact.setText(customer.getPhoneNo());
        dateDob.setValue(customer.getDob());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    private boolean validate() {
        if (!txtName.getText().matches("^[A-Za-z\\s]{1,30}$")) {
            lblErrorMsg.setText("Name can only contain letters and spaces (1-30 characters).");
            return false;
        }

        if (!txtEmail.getText().matches("^\\w+@\\w+\\.\\w+$\n")) {
            lblErrorMsg.setText("Email must be a valid email address.");
            return false;
        }

        if (!txtContact.getText().matches("^\\d{10}$")) {
            lblErrorMsg.setText("Phone number must be exactly 10 digits.");
            return false;
        }
        return true;
    }
}
