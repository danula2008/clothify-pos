package icet.edu.erp.controller.product;

import icet.edu.erp.dto.Product;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.ProductService;
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

public class ProductDashboardController implements Initializable {

    @FXML
    private TableColumn<String, Product> colBrand;

    @FXML
    private TableColumn<String, Product> colCategory;

    @FXML
    private TableColumn<Double, Product> colDiscount;

    @FXML
    private TableColumn<Integer, Product> colId;

    @FXML
    private TableColumn<String, Product> colName;

    @FXML
    private TableColumn<String, Product> colSize;

    @FXML
    private Group grpSelectedActions;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private TableView<Product> tblProduct;

    @FXML
    private TextField txtSearch;

    private ObservableList<Product> productList;

    private Product selectedData;

    private final ProductService service = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/product/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblProduct.setItems(productList);
        txtSearch.clear();
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteProduct();
    }

    @FXML
    void btnDeleteOnMouseClick(MouseEvent event) {
        deleteProduct();
    }

    private void deleteProduct(){
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to delete this record? %n%nID: %d%nName: %s%nCategory: %s%nBrand: %s%nSize: %s%nDiscount: %.2f",
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getCategory(),
                        selectedData.getBrand(),
                        selectedData.getSize(),
                        selectedData.getDiscount()))
        ){
            if (service.deleteProduct(selectedData.getId())) {
                ShowAlert.customAlert("Success", "Deleted Successfully.", Alert.AlertType.INFORMATION);

                loadData();
            } else {
                ShowAlert.customAlert("Error", "Could not Delete.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        editProduct();
    }

    @FXML
    void btnEditOnMouseClick(MouseEvent event) {
        editProduct();
    }

    private void editProduct() {
        if (
                ShowAlert.showConfirmationDialog(String.format("Are you sure you want to edit this record? %n%nID: %d%nName: %s%nCategory: %s%nBrand: %s%nSize: %s%nDiscount: %.2f",
                        selectedData.getId(),
                        selectedData.getName(),
                        selectedData.getCategory(),
                        selectedData.getBrand(),
                        selectedData.getSize(),
                        selectedData.getDiscount()))
        ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/data_form.fxml"));
                Parent root = loader.load();

                ProductDataFormController controller = loader.getController();
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
        tblProduct.getSelectionModel().clearSelection();
        grpSelectedActions.setVisible(false);
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblProduct.setItems(productList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblProduct.setItems(productList
                .filtered(product ->
                        product.getId().toString().toLowerCase().contains(searchTxt) ||
                        product.getName().toLowerCase().contains(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colId, colName, colCategory, colBrand, colSize, colDiscount};
        String[] colNames = {"id", "name", "category", "brand", "size", "discount"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }

        loadData();

        tblProduct.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                grpSelectedActions.setVisible(true);
            }
        });
    }

    private void loadData(){
        productList = service.getAllCustomers();
        tblProduct.setItems(productList);
    }
}
