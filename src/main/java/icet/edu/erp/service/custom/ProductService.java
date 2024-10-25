package icet.edu.erp.service.custom;

import icet.edu.erp.dto.Product;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.sql.SQLIntegrityConstraintViolationException;

public interface ProductService extends SuperService {
    Product getProduct(Integer txtProductId);
    ObservableList<Product> getAllCustomers();
    boolean addProduct(Product product) throws SQLIntegrityConstraintViolationException;
    boolean deleteProduct(Integer id);
    boolean updateProduct(Product product) throws SQLIntegrityConstraintViolationException;
    boolean hasId(Integer productId);
}
