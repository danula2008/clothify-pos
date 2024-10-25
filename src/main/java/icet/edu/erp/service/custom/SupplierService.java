package icet.edu.erp.service.custom;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;
import java.sql.SQLIntegrityConstraintViolationException;

public interface SupplierService extends SuperService {
    boolean deleteSupplier(Integer id);
    ObservableList<Supplier> getAllCustomers();
    Integer getId(JFXTextField txtSupplierId);
    Supplier getSupplier(JFXTextField txtProductId);
    boolean addSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException;
}
