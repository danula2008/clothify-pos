package icet.edu.erp.controller.user;
;
import icet.edu.erp.dto.User;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.UserService;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private TableColumn<String, User> colEmail;

    @FXML
    private TableColumn<Integer, User> colId;

    @FXML
    private TableColumn<Timestamp, User> colInitialLogin;

    @FXML
    private TableColumn<Timestamp, User> colLastLogin;

    @FXML
    private TableColumn<String, User> colName;

    @FXML
    private TableColumn<String, User> colRole;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TextField txtSearch;

    private ObservableList<User> userList;

    private User selectedData;

    private final UserService service = ServiceFactory.getInstance().getServiceType(ServiceType.USER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblUsers.setItems(userList);
        txtSearch.setText("");
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteUser();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteUser();
    }

    private void deleteUser(){
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to delete this record? %n%nID: %d%nName: %s%nEmail: %s%nRole: %s%nLast Login: %s%nInitial Login: %s",
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getEmail(),
                        selectedData.getRole(),
                        selectedData.getLastLogin(),
                        selectedData.getInitialLogin()))
            ){
            if (service.deleteUser(selectedData.getId())) {
                ShowAlert.customAlert("Success", "Deleted Successfully.", Alert.AlertType.INFORMATION);

                loadData();
            } else {
                ShowAlert.customAlert("Error", "Could not Delete.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        editUser();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editUser();
    }

    private void editUser() {
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to edit this record? %n%nID: %d%nName: %s%nEmail: %s%nRole: %s%nLast Login: %s%nInitial Login: %s",
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getEmail(),
                        selectedData.getRole(),
                        selectedData.getLastLogin(),
                        selectedData.getInitialLogin()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/supplier/data_form.fxml"));
                Parent root = loader.load();

                UserDataFormController controller = loader.getController();
                controller.setUser(selectedData);

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
        tblUsers.getSelectionModel().clearSelection();
        grpSelectedActions.setVisible(false);
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblUsers.setItems(userList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblUsers.setItems(userList
                .filtered(user ->
                        user.getId().toString().toLowerCase().contains(searchTxt) ||
                                user.getName().toLowerCase().contains(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colName, colEmail, colRole, colLastLogin, colInitialLogin};
        String[] colNames = {"id", "name", "email", "role", "lastLogin", "initialLogin"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }


        loadData();

        tblUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                grpSelectedActions.setVisible(true);
            }
        });
    }

    private void loadData(){
        userList = service.getAllCustomers();
        tblUsers.setItems(userList);
    }
}
