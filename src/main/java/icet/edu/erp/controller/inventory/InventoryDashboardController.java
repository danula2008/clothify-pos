package icet.edu.erp.controller.inventory;

import icet.edu.erp.dto.Inventory;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.InventoryService;
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
import java.util.ResourceBundle;

public class InventoryDashboardController implements Initializable {

    @FXML
    private TableColumn<Integer, Inventory> colId;

    @FXML
    private TableColumn<Double, Inventory> colInventoryPrice;

    @FXML
    private TableColumn<Integer, Inventory> colProductId;

    @FXML
    private TableColumn<Double, Inventory> colSellingPrice;

    @FXML
    private TableColumn<Inventory, Inventory> colSupplierId;

    @FXML
    private TableColumn<Integer, Inventory> colQtyOnHand;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<Inventory> tblInventory;

    @FXML
    private TextField txtSearch;

    private ObservableList<Inventory> inventoryList;

    private Inventory selectedData;

    private final InventoryService service = ServiceFactory.getInstance().getServiceType(ServiceType.INVENTORY);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/inventory/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblInventory.setItems(inventoryList);
        txtSearch.clear();
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteInventory();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteInventory();
    }

    private void deleteInventory(){
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to delete this record? %n%nID: %d%nProduct ID: %d%nSupplier ID: %d%nSelling Price: %.2f%nInventory Price: %.2f%nQuantity on Hand: %d",
                        selectedData.getId(),
                        selectedData.getProductId(),
                        selectedData.getSupplierId(),
                        selectedData.getSellingPrice(),
                        selectedData.getInventoryPrice(),
                        selectedData.getQtyOnHand()))
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
        editInventory();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editInventory();
    }

    private void editInventory() {
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to edit this record? %n%nID: %d%nProduct ID: %d%nSupplier ID: %d%nSelling Price: %.2f%nInventory Price: %.2f%nQuantity on Hand: %d",
                        selectedData.getId(),
                        selectedData.getProductId(),
                        selectedData.getSupplierId(),
                        selectedData.getSellingPrice(),
                        selectedData.getInventoryPrice(),
                        selectedData.getQtyOnHand()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inventory/data_form.fxml"));
                Parent root = loader.load();

                InventoryDataFormController controller = loader.getController();
                controller.setInventory(selectedData);

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
        tblInventory.getSelectionModel().clearSelection();
        grpSelectedActions.setVisible(false);
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblInventory.setItems(inventoryList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblInventory.setItems(inventoryList
                .filtered(inventory ->
                        inventory.getId().toString().toLowerCase().contains(searchTxt) ||
                        inventory.getProductId().toString().toLowerCase().contains(searchTxt) ||
                        inventory.getSupplierId().toString().toLowerCase().startsWith(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colProductId, colSupplierId, colSellingPrice, colInventoryPrice, colQtyOnHand};
        String[] colNames = new String[] {"id", "productId", "supplierId", "sellingPrice", "inventoryPrice", "qtyOnHand"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }


        loadData();

        tblInventory.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                grpSelectedActions.setVisible(true);
            }
        });
    }

    private void loadData(){
        inventoryList = service.getAllCustomers();
        tblInventory.setItems(inventoryList);
    }

}
