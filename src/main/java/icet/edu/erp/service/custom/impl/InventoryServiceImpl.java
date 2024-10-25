package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dto.Inventory;
import icet.edu.erp.service.custom.InventoryService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public class InventoryServiceImpl implements InventoryService {

    private static InventoryServiceImpl instance;
    private InventoryServiceImpl(){}
    public static InventoryService getInstance(){
        return instance==null? instance = new InventoryServiceImpl() : instance;
    }
    @Override
    public ObservableList<Inventory> getAllCustomers() {
        return null;
    }

    @Override
    public boolean addInventory(Inventory inventory) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
