package icet.edu.erp.controller.supplier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.SupplierService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;

public class SupplierDataFormController {

    @FXML
    private JFXButton btnDone;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    private final SupplierService service = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    private boolean isAdd = true;
    private Integer id;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate()) {
            return;
        }

        Supplier supplier = new Supplier(
                isAdd ? null : id,
                txtName.getText(),
                txtCompany.getText(),
                txtEmail.getText(),
                txtContact.getText(),
                0.0,
                LocalDate.now()
        );

        try {
            if (isAdd? service.addSupplier(supplier) : service.updateSupplier(supplier)) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtCompany, txtContact, txtName, txtEmail).forEach(JFXTextField::clear);
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

    private boolean validate() {
        if (txtName.getText().trim().isEmpty() || !txtName.getText().matches("^[A-Za-z\\s]+$")) {
            lblErrorMsg.setText("Name must not be empty and can only contain letters and spaces.");
            return false;
        }

        if (txtCompany.getText().trim().isEmpty()) {
            lblErrorMsg.setText("Company must not be empty.");
            return false;
        }

        if (!txtContact.getText().matches("^\\d{10}$")) {
            lblErrorMsg.setText("Phone number must be exactly 10 digits.");
            return false;
        }

        if (!txtEmail.getText().matches("^\\w+@\\w+\\.\\w+$\n")) {
            lblErrorMsg.setText("Email must be a valid email address.");
            return false;
        }

        return true;
    }

    public void setSupplier(Supplier selectedData) {
        isAdd = false;
        id = selectedData.getId();
        lblTitle.setText("Update Supplier");

        txtName.setText(selectedData.getName());
        txtEmail.setText(selectedData.getEmail());
        txtCompany.setText(selectedData.getCompany());
        txtContact.setText(selectedData.getContact());
    }
}
