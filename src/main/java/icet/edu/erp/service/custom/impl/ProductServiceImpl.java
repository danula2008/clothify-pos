package icet.edu.erp.service.custom.impl;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Product;
import icet.edu.erp.service.custom.ProductService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance;
    private ProductServiceImpl(){}
    public static ProductServiceImpl getInstance(){
        return instance == null? instance = new ProductServiceImpl() : instance;
    }

    @Override
    public Product getProduct(JFXTextField txtProductId) {
        return null;
    }

    @Override
    public Integer getId(JFXTextField txtProductId) {
        return null;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return false;
    }

    @Override
    public ObservableList<Product> getAllCustomers() {
        return null;
    }

    @Override
    public boolean addProduct(Product product) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
