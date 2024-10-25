package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Inventory;
import icet.edu.erp.dto.Product;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public interface InventoryService extends SuperService {
    ObservableList<Inventory> getAllCustomers();
    boolean addInventory(Inventory inventory) throws SQLIntegrityConstraintViolationException;
    boolean deleteEmployee(Integer id);
    boolean updateInventory(Inventory inventory) throws SQLIntegrityConstraintViolationException;
    Inventory getInventoryByProductId(Integer id);
}
