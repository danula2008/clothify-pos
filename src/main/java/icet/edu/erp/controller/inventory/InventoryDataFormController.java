package icet.edu.erp.controller.inventory;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Inventory;
import icet.edu.erp.dto.Product;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.InventoryService;
import icet.edu.erp.service.custom.ProductService;
import icet.edu.erp.service.custom.SupplierService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

public class InventoryDataFormController {

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtInventoryUnitPrice;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSellingUnitPrice;

    @FXML
    private JFXTextField txtSupplierId;

    private final InventoryService service = ServiceFactory.getInstance().getServiceType(ServiceType.INVENTORY);
    private final ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    private final SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);
    private boolean isAdd = true;
    private Integer id;

    @FXML
    void btnCheckProdIdOnAction(ActionEvent event) {
        if (!txtProductId.getText().matches("^\\d+$")){
            return;
        }
        Product product = productService.getProduct(Integer.parseInt(txtProductId.getText()));
        ShowAlert.customAlert("User Search Result",
                product == null?
                        "Could not find a product for the provided email address." :
                        "ID: %d%nName: %s%nCategory: %s%nBrand: %s%nSize: %s%nDiscount: %.2f".formatted(
                                product.getId(),
                                product.getName(),
                                product.getCategory(),
                                product.getBrand(),
                                product.getSize(),
                                product.getDiscount()),
                Alert.AlertType.INFORMATION
        );
    }

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate() || !productService.hasId(Integer.parseInt(txtProductId.getText())) || !supplierService.hasId(Integer.parseInt(txtSupplierId.getText()))) {
            return;
        }

        Inventory inventory = new Inventory(
                isAdd ? null : id,
                Integer.parseInt(txtProductId.getText()),
                Integer.parseInt(txtSupplierId.getText()),
                Double.parseDouble(txtSellingUnitPrice.getText()),
                Double.parseDouble(txtInventoryUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        try {
            if (isAdd? service.addInventory(inventory) : service.updateInventory(inventory)) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtProductId, txtQty, txtSupplierId, txtInventoryUnitPrice, txtSellingUnitPrice).forEach(JFXTextField::clear);
                } else {
                    Stage stage = (Stage) txtProductId.getScene().getWindow();
                    stage.close();
                }

            } else {
                ShowAlert.customAlert("Error", "Could not update the Database.\nPlease reload the table.", Alert.AlertType.ERROR);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            ShowAlert.customAlert("Item Code Error", "Enter a unique code for the item.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnSupIdOnAction(ActionEvent event) {
        if (!txtSupplierId.getText().matches("^\\d+$")){
            return;
        }

        Supplier supplier = supplierService.getSupplier(Integer.parseInt(txtSupplierId.getText()));
        ShowAlert.customAlert("User Search Result",
                supplier == null?
                        "Could not find a product for the provided email address." :
                        "ID: %d%nName: %s%nCompany: %s%nEmail: %s%nContact: %s%nPending Payment: %.2f%nLast Order Day: %s".formatted(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getCompany(),
                        supplier.getEmail(),
                        supplier.getContact(),
                        supplier.getPendingPayment(),
                        supplier.getLastOrderDay()),
                Alert.AlertType.INFORMATION
        );
    }

    public void setInventory(Inventory selectedData) {
        isAdd = false;
        id = selectedData.getId();
        lblTitle.setText("Update Inventory");

        txtProductId.setText(selectedData.getProductId().toString());
        txtSupplierId.setText(selectedData.getSupplierId().toString());
        txtSellingUnitPrice.setText(selectedData.getSellingPrice().toString());
        txtInventoryUnitPrice.setText(selectedData.getInventoryPrice().toString());
        txtQty.setText(selectedData.getQtyOnHand().toString());
    }

    private boolean validate() {
        if (!(txtProductId.getText().matches("^\\d+$") || txtSupplierId.getText().matches("^\\d+$"))){
            return false;
        }
        if (!txtSellingUnitPrice.getText().matches("^(\\d+(\\.\\d{1,2})?|\\.\\d{1,2})$")) {
            lblErrorMsg.setText("Selling Unit Price must be a positive number (up to 2 decimal places).");
            return false;
        }

        if (!txtInventoryUnitPrice.getText().matches("^(\\d+(\\.\\d{1,2})?|\\.\\d{1,2})$")) {
            lblErrorMsg.setText("Inventory Unit Price must be a positive number (up to 2 decimal places).");
            return false;
        }

        if (!txtQty.getText().matches("^[1-9]\\d*$")) {
            lblErrorMsg.setText("Quantity must be a positive integer.");
            return false;
        }
        return true;
    }

}
