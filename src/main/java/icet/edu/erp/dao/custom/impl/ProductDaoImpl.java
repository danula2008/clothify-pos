package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.ProductDao;
import icet.edu.erp.entity.ProductEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDaoImpl implements ProductDao {

    private static ProductDaoImpl instance;

    private ProductDaoImpl(){}

    public static ProductDaoImpl getInstance() {
        return instance==null? instance = new ProductDaoImpl() : instance;
    }

    @Override
    public boolean save(ProductEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Product (name, category, brand, size, discount) VALUES (?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getCategory(),
                    entity.getBrand(),
                    entity.getSize(),
                    entity.getDiscount());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during save.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(ProductEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE Product SET name = ?, category = ?, brand = ?, size = ?, discount = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getCategory(),
                    entity.getBrand(),
                    entity.getSize(),
                    entity.getDiscount(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during update.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        List<ProductEntity> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                ProductEntity entity = new ProductEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("brand"),
                        resultSet.getString("size"),
                        resultSet.getDouble("discount")
                );
                productList.add(entity);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return productList;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<Integer> getIDs() {
        List<Integer> idList = new ArrayList<>();
        String sql = "SELECT id FROM Product";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return idList;
    }

    @Override
    public ProductEntity getItem(Integer id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new ProductEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("brand"),
                        resultSet.getString("size"),
                        resultSet.getDouble("discount")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }
}