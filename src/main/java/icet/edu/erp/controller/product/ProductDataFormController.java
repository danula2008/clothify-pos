package icet.edu.erp.controller.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.dto.Product;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.ProductService;
import icet.edu.erp.service.custom.UserService;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProductDataFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private Label lblErrorMsg;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtBrand;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProductSize;

    private final ProductService service = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    private boolean isAdd = true;
    private Integer id;

    @FXML
    void btnDoneOnAction(ActionEvent event) {
        if (!validate()) {
            return;
        }

        try {
            if (service.addProduct(new Product(
                    isAdd ? null : id,
                    txtName.getText(),
                    cmbCategory.getValue(),
                    txtBrand.getText(),
                    txtProductSize.getText(),
                    Double.parseDouble(txtDiscount.getText())
                    )
            )) {
                ShowAlert.customAlert("Success", "Successfully updated the Database.\nPlease reload the table.", Alert.AlertType.INFORMATION);

                if (isAdd) {
                    Arrays.asList(txtBrand, txtDiscount, txtName, txtProductSize).forEach(JFXTextField::clear);
                    cmbCategory.setValue("Men's");
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

    public void setInventory(Product selectedData) {
        isAdd = false;
        id = selectedData.getId();
        lblTitle.setText("Update Product");

        txtName.setText(selectedData.getName());
        cmbCategory.setValue(selectedData.getCategory());
        txtBrand.setText(selectedData.getBrand());
        txtProductSize.setText(selectedData.getSize());
        txtDiscount.setText(selectedData.getDiscount().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbCategory.setItems(FXCollections.observableArrayList("Men's", "Women's", "Children's", "Accessories", "Footwear", "Athletic Wear", "Outerwear", "Sleepwear", "Formal Wear", "Casual Wear"));
    }

    private boolean validate() {
        if (txtBrand.getText().trim().isEmpty()) {
            lblErrorMsg.setText("Brand must not be empty.");
            return false;
        }

        if (!txtDiscount.getText().matches("^(100|\\d{1,2}(\\.\\d{1,2})?|\\.\\d{1,2})$")) {
            lblErrorMsg.setText("Discount must be a number between 0 and 100 (up to 2 decimal places).");
            return false;
        }

        if (txtName.getText().trim().isEmpty() || !txtName.getText().matches("^[A-Za-z\\s]+$")) {
            lblErrorMsg.setText("Name must not be empty and can only contain letters and spaces.");
            return false;
        }

        if (txtProductSize.getText().trim().isEmpty()) {
            lblErrorMsg.setText("Product size must not be empty.");
            return false;
        }
        return true;
    }

}
