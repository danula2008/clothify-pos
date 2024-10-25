package icet.edu.erp.controller.employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.EmployeeService;
import icet.edu.erp.service.custom.UserService;
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
import java.util.Arrays;
import java.util.ResourceBundle;

public class EmployeeDataFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private DatePicker dateBDay;

    @FXML
    private DatePicker dateHireDate;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtUserEmail;

    private final EmployeeService service = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
    private final UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);
    private boolean isAdd = true;
    private Integer id;

    @FXML
    void btnCheckEmailOnAction(ActionEvent event) {
        Employee employee = ((EmployeeService) ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE)).getEmployeeByUserId(
                ((UserService)ServiceFactory.getInstance().getServiceType(ServiceType.USER)).getUserId(txtUserEmail.getText())
        );
        ShowAlert.customAlert("User Search Result",
                    employee == null?
                            "Could not find a employee for the provided email address." :
                            "ID: %d%nGender: %s%nPhone No: %s%nDate of Birth: %s%nHire Date: %s%nSalary: %.2f".formatted(
                                    employee.getId(),
                                    employee.getGender(),
                                    employee.getPhoneNo(),
                                    employee.getDob(),
                                    employee.getHireDate(),
                                    employee.getSalary()),
                    Alert.AlertType.INFORMATION
                );
    }

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate() || userService.isEmailInSystem(txtUserEmail.getText())) {
            return;
        }

        Employee employee = new Employee(
                isAdd ? null : id,
                userService.getUserId(txtUserEmail.getText()),
                cmbGender.getValue(),
                txtContact.getText(),
                dateHireDate.getValue(),
                dateBDay.getValue(),
                Double.parseDouble(txtSalary.getText())
        );

        try {
            if (isAdd? service.addEmployee(employee) : service.updateEmployee(employee)) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtUserEmail, txtSalary, txtContact).forEach(JFXTextField::clear);
                    cmbGender.setValue("Male");
                    dateBDay.setValue(null);
                    dateHireDate.setValue(null);
                } else {
                    Stage stage = (Stage) txtUserEmail.getScene().getWindow();
                    stage.close();
                }

            } else {
                ShowAlert.customAlert("Error", "Could not update the Database.\nPlease reload the table.", Alert.AlertType.ERROR);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            ShowAlert.customAlert("Item Code Error", "Enter a unique code for the item.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    public void setEmployee(Employee selectedData) {
        isAdd = false;
        id = selectedData.getId();
        lblTitle.setText("Update Employee");

        txtUserEmail.setText(userService.getEmail(selectedData.getUserId()));
        txtContact.setText(selectedData.getPhoneNo());
        txtSalary.setText(selectedData.getSalary().toString());
        cmbGender.setValue(selectedData.getGender());
        dateBDay.setValue(selectedData.getDob());
        dateHireDate.setValue(selectedData.getHireDate());
    }

    private boolean validate() {
        if (!txtUserEmail.getText().matches("^\\w+@\\w+\\.\\w+$\n")) {
            lblErrorMsg.setText("Email must be a valid email address.");
            return false;
        }

        if (!txtSalary.getText().matches("^(\\d+(\\.\\d{1,2})?|\\.\\d{1,2})$")) {
            lblErrorMsg.setText("Salary must be a positive number (up to 2 decimal places).");
            return false;
        }

        if (!txtContact.getText().matches("^\\d{10}$")) {
            lblErrorMsg.setText("Phone number must be exactly 10 digits.");
            return false;
        }
        return true;
    }
}
