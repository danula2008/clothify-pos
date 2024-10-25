package icet.edu.erp.controller.order;

import icet.edu.erp.dto.Order;
import icet.edu.erp.service.ServiceFactory;
import icet.edu.erp.service.custom.OrderService;
import icet.edu.erp.util.ServiceType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class OrderDashboardController implements Initializable {

    @FXML
    private TableColumn<Integer, Order> colCustId;

    @FXML
    private TableColumn<Integer, Order> colEmpId;

    @FXML
    private TableColumn<Integer, Order> colId;

    @FXML
    private TableColumn<String, Order> colPaymentType;

    @FXML
    private TableColumn<String, Order> colReturnStatus;

    @FXML
    private TableColumn<Timestamp, Order> colTimeStamp;

    @FXML
    private TableColumn<Double, Order> colTotCost;

    @FXML
    private TableColumn<Double, Order> colTotDisc;

    @FXML
    private ImageView imgCancelSearch;

    @FXML
    private HBox selectedView;

    @FXML
    private TableView<Order> tblOrder;

    @FXML
    private TextField txtSearch;

    private ObservableList<Order> orderList;

    private Order selectedData;

    private final OrderService service = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);


    @FXML
    void btnCancelSearchOnAction(ActionEvent event) {
        tblOrder.setItems(orderList);
        txtSearch.setText("");
        imgCancelSearch.setVisible(false);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadData();
        tblOrder.getSelectionModel().clearSelection();
        selectedView.setVisible(false);
    }

    @FXML
    void btnViewProductsOnAction(ActionEvent event) {
        //TODO
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchTxt = txtSearch.getText().toLowerCase();

        if (searchTxt.isEmpty()){
            tblOrder.setItems(orderList);
            imgCancelSearch.setVisible(false);
            return;
        }

        imgCancelSearch.setVisible(true);
        tblOrder.setItems(orderList
                .filtered(order ->
                        order.getId().toString().toLowerCase().contains(searchTxt) ||
                                order.getEmpId().toString().contains(searchTxt) ||
                                order.getCustId().toString().contains(searchTxt))
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<?, ?>[] cols = new TableColumn<?, ?>[] {
                colId, colCustId, colEmpId, colTimeStamp, colPaymentType, colTotDisc, colTotCost, colReturnStatus
        };
        String[] colNames = new String[] {
                "id", "custId", "empId", "dateTime", "paymentType", "totalDiscount", "totalCost", "returnStatus"
        };
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }

        loadData();

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                selectedData = newValue;
                selectedView.setVisible(true);
            }
        });
    }

    private void loadData(){
        orderList = service.getAllCustomers();
        tblOrder.setItems(orderList);
    }
}
