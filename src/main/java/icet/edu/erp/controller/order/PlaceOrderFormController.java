package icet.edu.erp.controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.*;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.*;
import icet.edu.erp.util.ServiceType;
import icet.edu.erp.util.ShowAlert;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXButton btnCustomerOk;

    @FXML
    private JFXButton btnProductOk;

    @FXML
    private TableColumn<Integer, OrderProduct> colCode;

    @FXML
    private TableColumn<String, OrderProduct> colName;

    @FXML
    private TableColumn<Double, OrderProduct> colDiscount;

    @FXML
    private TableColumn<Double, OrderProduct> colNetTotal;

    @FXML
    private TableColumn<Integer, OrderProduct> colQty;

    @FXML
    private TableColumn<Double, OrderProduct> colTotal;

    @FXML
    private TableColumn<Double, OrderProduct> colUnitPrice;

    @FXML
    private ImageView imgCustomerOk;

    @FXML
    private ImageView imgProductOk;

    @FXML
    private Label lblCContact;

    @FXML
    private Label lblCDob;

    @FXML
    private Label lblCEmail;

    @FXML
    private Label lblCId;

    @FXML
    private Label lblCName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblGrandTotal;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblPCode;

    @FXML
    private Label lblPName;

    @FXML
    private Label lblPDiscount;

    @FXML
    private Label lblPSize;

    @FXML
    private Label lblPUnitPrice;

    @FXML
    private Label lblPQtyOnHand;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalDiscount;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<OrderProduct> tblCart;

    @FXML
    private TextField txtCustomerSearch;

    @FXML
    private JFXTextField txtPQtyOrdered;

    @FXML
    private TextField txtProductSearch;

    private final OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);
    private final CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    private final ProductService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    private final InventoryService inventoryService = ServiceFactory.getInstance().getServiceType(ServiceType.INVENTORY);

    private ObservableList<OrderProduct> cart = FXCollections.observableArrayList();
    private User user;
    private Double totNetTotal = 0.0;
    private Double totDiscount = 0.0;
    private Double totGrandTotal = 0.0;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer/data_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        if (txtPQtyOrdered.getText().matches("^[1-9][0-9]*$")){
            int pQtyOrdered = Integer.parseInt(txtPQtyOrdered.getText());
            if (pQtyOrdered >= Integer.parseInt(lblPQtyOnHand.getText())){
                ShowAlert.customAlert("Error", "Do not have sufficient stocks.", Alert.AlertType.ERROR);
                return;
            }

            double netTotal = pQtyOrdered * Double.parseDouble(lblPUnitPrice.getText());

            cart.add(new OrderProduct(
                    null,
                    Integer.parseInt(lblPCode.getText()),
                    Double.parseDouble(lblPUnitPrice.getText()),
                    Integer.parseInt(txtPQtyOrdered.getText()),
                    Double.parseDouble(lblPDiscount.getText()),
                    netTotal - Double.parseDouble(lblPDiscount.getText())
            ));
            tblCart.setItems(cart);

            setTotalsToLabels(netTotal,0.0,netTotal - 0.0);

            txtProductSearch.setText("P");
            Arrays.asList(lblPCode, lblPUnitPrice, lblDate, lblPQtyOnHand).forEach(label -> label.setText(""));
            txtPQtyOrdered.setText("");

            imgProductOk.setVisible(false);
            btnProductOk.setVisible(false);
            return;
        }
        ShowAlert.customAlert("Error", "Invalid ordered quantity.", Alert.AlertType.ERROR);
    }

    @FXML
    void btnCancelOrderOnAction(ActionEvent event) {
        cart = FXCollections.observableArrayList();
        tblCart.setItems(cart);
        Arrays.asList(lblPCode, lblPUnitPrice, lblPDiscount, lblPName, lblPSize, lblPQtyOnHand, lblCContact, lblCDob, lblCEmail, lblCId, lblCName).forEach(label -> label.setText(""));
        Arrays.asList(lblNetTotal, lblTotalDiscount, lblGrandTotal).forEach(label -> label.setText("Rs. 0"));
    }

    @FXML
    void btnCustomerOkSearchOnAction(ActionEvent event) {
        if (!txtCustomerSearch.getText().matches("^\\d+$")){
            return;
        }

        Customer customerSelected = customerService.getCustomer(Integer.parseInt(txtCustomerSearch.getText()));
        if (customerSelected ==null){
            ShowAlert.customAlert("No Item", "No item found for the given item code.", Alert.AlertType.ERROR);
        } else {
            lblCId.setText(customerSelected.getId().toString());
            lblCName.setText(customerSelected.getName());
            lblCContact.setText(customerSelected.getPhoneNo());
            lblCEmail.setText(customerSelected.getEmail());
            lblCDob.setText(customerSelected.getDob().toString());

            imgCustomerOk.setVisible(false);
            btnCustomerOk.setVisible(false);
        }
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
//        userService.updateLastLogout(user.getId(), Timestamp.valueOf(LocalDateTime.now()));
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            ShowAlert.fileNotFoundError();
        }
        Stage stage = (Stage) lblUserName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        ArrayList<OrderProduct> orderDetails = new ArrayList<>();
        cart.forEach(cartProducts -> {
            orderDetails.add(new OrderProduct(
                            null,
                            Integer.parseInt(lblPCode.getText()),
                            Double.parseDouble(lblPUnitPrice.getText()),
                            Integer.parseInt(txtPQtyOrdered.getText()),
                            Double.parseDouble(lblPDiscount.getText()),
                    Integer.parseInt(txtPQtyOrdered.getText()) * Double.parseDouble(lblPUnitPrice.getText()) - Double.parseDouble(lblPDiscount.getText())
            ));
        });
        try {
            boolean transactionSuccess = orderService.placeOrder(new Order(
                    null,
                    Integer.parseInt(lblCId.getText()),
                    user.getId(),
                    Timestamp.valueOf(LocalDateTime.now()),
                    "Cash",
                    totNetTotal,
                    totDiscount,
                    totGrandTotal));

            if (transactionSuccess) {
                ShowAlert.customAlert("Success", "Order places successfully!", Alert.AlertType.INFORMATION);
            } else {
                ShowAlert.customAlert("Failed", "Failed to place the order!", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
    }

    @FXML
    void btnProductsOkSearchOnAction(ActionEvent event) {
        Product itemSelected = itemService.getProduct(Integer.parseInt(txtProductSearch.getText()));
        if (itemSelected ==null){
            ShowAlert.customAlert("No Item", "No item found for the given item code.", Alert.AlertType.ERROR);
        }
        else {
            lblPCode.setText(itemSelected.getId().toString());
            lblPName.setText(itemSelected.getName());
            lblPDiscount.setText(itemSelected.getDiscount().toString());
            lblPUnitPrice.setText(inventoryService.getInventoryByProductId(itemSelected.getId()).getSellingPrice().toString());
            lblPQtyOnHand.setText(inventoryService.getInventoryByProductId(itemSelected.getId()).getQtyOnHand().toString());
        }
    }

    @FXML
    void txtCustomerKeyPressed(KeyEvent event) {
        imgCustomerOk.setVisible(!txtCustomerSearch.getText().isEmpty());
        btnCustomerOk.setVisible(!txtCustomerSearch.getText().isEmpty());
    }

    @FXML
    void txtProductKeyPressed(KeyEvent event) {
        imgProductOk.setVisible(!txtProductSearch.getText().isEmpty());
        btnProductOk.setVisible(!txtProductSearch.getText().isEmpty());
    }

    private void setTotalsToLabels(Double totNetTotal, Double totDiscount, Double totGrandTotal){

        this.totNetTotal += totNetTotal;
        this.totDiscount += totDiscount;
        this.totGrandTotal += totGrandTotal;

        lblNetTotal.setText("Rs. " + this.totNetTotal.toString());
        lblTotalDiscount.setText("Rs. " + this.totDiscount.toString());
        lblGrandTotal.setText("Rs. " + this.totGrandTotal.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {colCode, colName, colQty, colUnitPrice, colNetTotal, colDiscount, colTotal};
        String[] colNames = new String[] {"itemCode", "description", "orderQty", "unitPrice", "netTotal", "discount", "total"};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }

        loadDateAndTime();
    }


    public void setUserData(User user) {
        this.user = user;
        lblUserName.setText(user.getName());
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

//        -----------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(String.format("%02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond()));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
