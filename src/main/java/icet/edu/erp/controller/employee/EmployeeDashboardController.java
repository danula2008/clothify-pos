package icet.edu.erp.controller.employee;

import icet.edu.erp.dto.Employee;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.EmployeeService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {

    @FXML
    private TableColumn<LocalDate, Employee> colDob;

    @FXML
    private TableColumn<String, Employee> colGender;

    @FXML
    private TableColumn<LocalDate, Employee> colHireDate;

    @FXML
    private TableColumn<Integer, Employee> colId;

    @FXML
    private TableColumn<String, Employee> colPhoneNo;

    @FXML
    private TableColumn<Double, Employee> colSalary;

    @FXML
    private TableColumn<Integer, Employee> colUserId;

    @FXML
    private TableColumn<String, Employee> colUserName;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtSearch;

    private ObservableList<Employee> employeeList;

    private Employee selectedData;

    private final EmployeeService service = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employee/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblEmployee.setItems(employeeList);
        txtSearch.clear();
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteEmployee();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteEmployee();
    }

    private void deleteEmployee(){
        if (
                ShowAlert.showConfirmationDialog("Are you sure you want to delete this record? %n%nID: %d%nGender: %s%nPhone No: %s%nDate of Birth: %s%nHire Date: %s%nSalary: %.2f".formatted(
                        selectedData.getId(),
                        selectedData.getGender(),
                        selectedData.getPhoneNo(),
                        selectedData.getDob(),
                        selectedData.getHireDate(),
                        selectedData.getSalary()))
        ){
            if (service.deleteEmployee(selectedData.getId())) {
                ShowAlert.customAlert("Success", "Deleted Successfully.", Alert.AlertType.INFORMATION);

                loadData();
            } else {
                ShowAlert.customAlert("Error", "Could not Delete.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        editEmployee();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editEmployee();
    }

    private void editEmployee() {
        if (
                ShowAlert.showConfirmationDialog("Are you sure you want to edit this record? %n%nID: %d%nGender: %s%nPhone No: %s%nDate of Birth: %s%nHire Date: %s%nSalary: %.2f".formatted(
                        selectedData.getId(),
                        selectedData.getGender(),
                        selectedData.getPhoneNo(),
                        selectedData.getDob(),
                        selectedData.getHireDate(),
                        selectedData.getSalary()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee/data_form.fxml"));
                Parent root = loader.load();

                EmployeeDataFormController controller = loader.getController();
                controller.setEmployee(selectedData);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                ShowAlert.fileNotFoundError();
            }
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadData();
        tblEmployee.getSelectionModel().clearSelection();
        grpSelectedActions.setVisible(false);
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblEmployee.setItems(employeeList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblEmployee.setItems(employeeList
                .filtered(employee ->
                        employee.getId().toString().contains(searchTxt) ||
                        employee.getUserId().toString().startsWith(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colUserId, colUserName, colGender, colPhoneNo, colHireDate, colDob, colSalary};
        String[] colNames = new String[] {"id", "userId", "username", "gender", "phoneNo", "hireDate", "dob", "salary"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }


        loadData();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                grpSelectedActions.setVisible(true);
            }
        });
    }

    private void loadData(){
        employeeList = service.getAllEmployees();
        tblEmployee.setItems(employeeList);
    }
}
