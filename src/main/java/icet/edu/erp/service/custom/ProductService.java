package icet.edu.erp.service.custom;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Product;
import icet.edu.erp.service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public interface ProductService extends SuperService {
    Product getProduct(JFXTextField txtProductId);
    Integer getId(JFXTextField txtProductId);
    ObservableList<Product> getAllCustomers();
    boolean addProduct(Product product) throws SQLIntegrityConstraintViolationException;
    boolean deleteProduct(Integer id);
}
