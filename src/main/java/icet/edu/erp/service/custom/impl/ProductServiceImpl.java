package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.ProductDao;
import icet.edu.erp.dto.Product;
import icet.edu.erp.entity.ProductEntity;
import icet.edu.erp.service.custom.ProductService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.modelmapper.ModelMapper;

import java.sql.SQLIntegrityConstraintViolationException;

public class ProductServiceImpl implements ProductService

{
    private static ProductServiceImpl instance;
    private ProductServiceImpl(){}
    public static ProductServiceImpl getInstance(){
        return instance == null? instance = new ProductServiceImpl() : instance;
    }

    private final ProductDao repository = DaoFactory.getInstance().getDaoType(DaoType.PRODUCT);
    private final ModelMapper mapper = new ModelMapper();


    @Override
    public Product getProduct(Integer productId) {
        return mapper.map(repository.getItem(productId), Product.class);
    }


    @Override
    public boolean deleteProduct(Integer id) {
        return repository.delete(id);
    }

    @Override
    public boolean updateProduct(Product product) throws SQLIntegrityConstraintViolationException {
        return repository.update(mapper.map(product, ProductEntity.class));
    }

    @Override
    public boolean hasId(Integer productId) {
       return repository.getItem(productId) != null;
    }

    @Override
    public ObservableList<Product> getAllCustomers() {
        return (ObservableList<Product>) repository.findAll().stream().map(productEntity -> mapper.map(productEntity, Product.class)).toList();
    }

    @Override
    public boolean addProduct(Product product) throws SQLIntegrityConstraintViolationException {
        return repository.save(mapper.map(product, ProductEntity.class));
    }
}
