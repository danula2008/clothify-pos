package icet.edu.erp.dao.custom;

import icet.edu.erp.dao.CrudRepository;
import icet.edu.erp.dto.Employee;
import icet.edu.erp.entity.EmployeeEntity;

public interface EmployeeDao extends CrudRepository<EmployeeEntity> {
    EmployeeEntity getItemByUserId(Integer userId);
}
