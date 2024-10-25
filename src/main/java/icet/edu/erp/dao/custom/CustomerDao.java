package icet.edu.erp.dao.custom;

import icet.edu.erp.dao.CrudRepository;
import icet.edu.erp.entity.CustomerEntity;

import java.time.LocalDate;

public interface CustomerDao extends CrudRepository<CustomerEntity> {
    LocalDate getDate(Integer id);
}
