package icet.edu.erp.controller.supplier;

import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.SupplierService;
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

public class SupplierDashboardController implements Initializable {

    @FXML
    private TableColumn<String, Supplier> colCompany;

    @FXML
    private TableColumn<String, Supplier> colContact;

    @FXML
    private TableColumn<String, Supplier> colEmail;

    @FXML
    private TableColumn<Integer, Supplier> colId;

    @FXML
    private TableColumn<LocalDate, Supplier> colLastOrderDate;

    @FXML
    private TableColumn<String, Supplier> colName;

    @FXML
    private TableColumn<Double, Supplier> colPendingPayment;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtSearch;

    private ObservableList<Supplier> supplierList;

    private Supplier selectedData;

    private final SupplierService service = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/supplier/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblSupplier.setItems(supplierList);
        txtSearch.clear();
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteSupplier();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteSupplier();
    }

    private void deleteSupplier(){
        if (
            ShowAlert.showConfirmationDialog(String.format("Are you sure you want to delete this record? %n%nID: %d%nName: %s%nCompany: %s%nEmail: %s%nContact: %s%nPending Payment: %.2f%nLast Order Day: %s",
                    selectedData.getId(),
                    selectedData.getName(),
                    selectedData.getCompany(),
                    selectedData.getEmail(),
                    selectedData.getContact(),
                    selectedData.getPendingPayment(),
                    selectedData.getLastOrderDay())
        )){
            if (service.deleteSupplier(selectedData.getId())) {
                ShowAlert.customAlert("Success", "Deleted Successfully.", Alert.AlertType.INFORMATION);

                loadData();
            } else {
                ShowAlert.customAlert("Error", "Could not Delete.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        editSupplier();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editSupplier();
    }

    private void editSupplier() {
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to edit this record? %n%nID: %d%nName: %s%nCompany: %s%nEmail: %s%nContact: %s%nPending Payment: %.2f%nLast Order Day: %s",
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getCompany(),
                        selectedData.getEmail(),
                        selectedData.getContact(),
                        selectedData.getPendingPayment(),
                        selectedData.getLastOrderDay()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/supplier/data_form.fxml"));
                Parent root = loader.load();

                SupplierDataFormController controller = loader.getController();
                controller.setSupplier(selectedData);

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
        tblSupplier.getSelectionModel().clearSelection();
        grpSelectedActions.setVisible(false);
    }

    @FXML
    void btnViewProductsOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblSupplier.setItems(supplierList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblSupplier.setItems(supplierList
                .filtered(product ->
                        product.getId().toString().toLowerCase().contains(searchTxt) ||
                        product.getName().toLowerCase().contains(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colName, colCompany, colEmail, colContact, colPendingPayment, colLastOrderDate};
        String[] colNames = {"id", "name", "company", "email", "contact", "pendingPayment", "lastOrderDay"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }


        loadData();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                grpSelectedActions.setVisible(true);
            }
        });
    }

    private void loadData(){
        supplierList = service.getAllCustomers();
        tblSupplier.setItems(supplierList);
    }
}
