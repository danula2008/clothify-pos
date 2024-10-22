package icet.edu.erp.controller;

import com.jfoenix.controls.JFXButton;
import icet.edu.erp.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainDashboardFormController implements Initializable {

    @FXML
    private JFXButton btnCustomers;

    @FXML
    private JFXButton btnEmployees;

    @FXML
    private JFXButton btnInventory;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnProducts;

    @FXML
    private JFXButton btnSuppliers;

    @FXML
    private Label lblUser;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        loadToAnchorPane("customer", btnCustomers);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        loadToAnchorPane("employee", btnEmployees);
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {
        loadToAnchorPane("inventory", btnInventory);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user/login_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
        Stage stage = (Stage) lblUser.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        loadToAnchorPane("order", btnOrders);
    }

    @FXML
    void btnProductOnAction(ActionEvent event) {
        loadToAnchorPane("product", btnProducts);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {
        loadToAnchorPane("supplier", btnSuppliers);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadToAnchorPane("product", btnProducts);
    }

    private void loadToAnchorPane(String location, JFXButton btn) {
        try {
            rootPane.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/view/" + location + "/dashboard_form.fxml")));
            Arrays.stream(new JFXButton[] {btnCustomers, btnEmployees, btnInventory, btnProducts, btnSuppliers, btnOrders}).forEach(button -> button.setStyle("-fx-background-color: white; -fx-text-fill: #6a6a6a;"));
            btn.setStyle("-fx-text-fill: white; -fx-background-color: #1230AE;");
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }
}
