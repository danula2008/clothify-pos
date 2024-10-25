package icet.edu.erp.service.custom;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;
import java.sql.SQLIntegrityConstraintViolationException;

public interface SupplierService extends SuperService {
    boolean deleteSupplier(Integer id);
    ObservableList<Supplier> getAllCustomers();
    Supplier getSupplier(Integer supplierId);
    boolean addSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException;
    boolean hasId(int supplierId);
    boolean updateSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException;
}
