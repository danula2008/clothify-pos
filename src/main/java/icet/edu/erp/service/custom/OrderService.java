package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Order;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;

public interface OrderService extends SuperService {
    ObservableList<Order> getAllCustomers();
}
