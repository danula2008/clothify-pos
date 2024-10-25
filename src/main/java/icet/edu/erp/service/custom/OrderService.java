package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Order;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public interface OrderService extends SuperService {
    ObservableList<Order> getAllCustomers();
    boolean placeOrder(Order cash) throws SQLIntegrityConstraintViolationException;
}
