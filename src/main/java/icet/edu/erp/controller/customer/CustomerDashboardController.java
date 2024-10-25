package icet.edu.erp.controller.customer;

import icet.edu.erp.dto.Customer;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.CustomerService;
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

public class CustomerDashboardController implements Initializable {

    @FXML
    private Group btnEditBtnDelete;

    @FXML
    private TableColumn<LocalDate, Customer> colDob;

    @FXML
    private TableColumn<String, Customer> colEmail;

    @FXML
    private TableColumn<String, Customer> colGender;

    @FXML
    private TableColumn<Integer, Customer> colId;

    @FXML
    private TableColumn<LocalDate, Customer> colJoinDate;

    @FXML
    private TableColumn<String, Customer> colLoyaltyTier;

    @FXML
    private TableColumn<String, Customer> colName;

    @FXML
    private TableColumn<String, Customer> colPhoneNo;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TextField txtSearch;

    private ObservableList<Customer> customerList;

    private Customer selectedData;

    private final CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblCustomers.setItems(customerList);
        txtSearch.clear();
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteCustomer();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteCustomer();
    }

    private void deleteCustomer(){
        if (
            ShowAlert.showConfirmationDialog("Are you sure you want to delete this record? %n%nCode: %s%nName: %s%nGender: %s%nEmail: %s%nPhone No: %s%nDate of Birth: %s%nJoined Date: %s%nLoyalty Tier: %s".formatted(
                    selectedData.getId(),
                    selectedData.getName(),
                    selectedData.getGender(),
                    selectedData.getEmail(),
                    selectedData.getPhoneNo(),
                    selectedData.getDob(),
                    selectedData.getJoinedDate(),
                    selectedData.getLoyaltyTier()))
        ){
            if (service.deleteCustomer(selectedData.getId())) {
                ShowAlert.customAlert("Success", "Deleted Successfully.", Alert.AlertType.INFORMATION);

                loadData();
            } else {
                ShowAlert.customAlert("Error", "Could not Delete.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        editCustomer();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editCustomer();
    }

    private void editCustomer() {
        if (
                ShowAlert.showConfirmationDialog("Are you sure you want to edit this record? %n%nCode: %s%nName: %s%nGender: %s%nEmail: %s%nPhone No: %s%nDate of Birth: %s%nJoined Date: %s%nLoyalty Tier: %s".formatted(
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getGender(),
                        selectedData.getEmail(),
                        selectedData.getPhoneNo(),
                        selectedData.getDob(),
                        selectedData.getJoinedDate(),
                        selectedData.getLoyaltyTier()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customer/data_form.fxml"));
                Parent root = loader.load();

                CustomerDataFormController controller = loader.getController();
                controller.setCustomer(selectedData);

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
        tblCustomers.getSelectionModel().clearSelection();
        btnEditBtnDelete.setVisible(false);
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblCustomers.setItems(customerList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblCustomers.setItems(customerList
                .filtered(customer ->
                        customer.getId().toString().contains(searchTxt) ||
                        customer.getName().toLowerCase().startsWith(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colName, colGender, colEmail, colPhoneNo, colDob, colJoinDate, colLoyaltyTier};
        String[] colNames = new String[] {"id", "name", "gender", "email", "phoneNo", "dob", "joinedDate", "loyaltyTier"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }


        loadData();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                btnEditBtnDelete.setVisible(true);
            }
        });
    }

    private void loadData(){
        customerList = service.getAllCustomers();
        tblCustomers.setItems(customerList);
    }
}
