package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.InventoryDao;
import icet.edu.erp.dto.Inventory;
import icet.edu.erp.entity.InventoryEntity;
import icet.edu.erp.service.custom.InventoryService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.sql.SQLIntegrityConstraintViolationException;

public class InventoryServiceImpl implements InventoryService {

    private static InventoryServiceImpl instance;
    private InventoryServiceImpl(){}
    public static InventoryService getInstance(){
        return instance==null? instance = new InventoryServiceImpl() : instance;
    }

    private final InventoryDao repository = DaoFactory.getInstance().getDaoType(DaoType.INVENTORY);
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public ObservableList<Inventory> getAllCustomers() {
        return (ObservableList<Inventory>) repository.findAll().stream().map(inventoryEntity -> mapper.map(inventoryEntity, Inventory.class)).toList();
    }

    @Override
    public boolean addInventory(Inventory inventory) throws SQLIntegrityConstraintViolationException {
       return repository.save(mapper.map(inventory, InventoryEntity.class));
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        return repository.delete(id);
    }

    @Override
    public boolean updateInventory(Inventory inventory) throws SQLIntegrityConstraintViolationException {
        return repository.update(mapper.map(inventory, InventoryEntity.class));
    }

    @Override
    public Inventory getInventoryByProductId(Integer id) {
        return mapper.map(repository.findByProductId(id), Inventory.class);
    }
}
