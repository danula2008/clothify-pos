package icet.edu.erp.dao.custom;

import icet.edu.erp.dao.CrudRepository;
import icet.edu.erp.entity.InventoryEntity;

public interface InventoryDao extends CrudRepository<InventoryEntity> {
    InventoryEntity findByProductId(Integer id);
}
