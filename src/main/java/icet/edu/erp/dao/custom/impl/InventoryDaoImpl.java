package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.InventoryDao;
import icet.edu.erp.entity.InventoryEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {

    private static InventoryDaoImpl instance;

    private InventoryDaoImpl(){}

    public static InventoryDaoImpl getInstance() {
        return instance==null? instance = new InventoryDaoImpl() : instance;
    }

    @Override
    public boolean save(InventoryEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Inventory (productId, supplierId, sellingPrice, inventoryPrice, qtyOnHand) VALUES (?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql, entity.getProductId(), entity.getSupplierId(), entity.getSellingPrice(), entity.getInventoryPrice(), entity.getQtyOnHand());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during save.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(InventoryEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE Inventory SET productId = ?, supplierId = ?, sellingPrice = ?, inventoryPrice = ?, qtyOnHand = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql, entity.getProductId(), entity.getSupplierId(), entity.getSellingPrice(), entity.getInventoryPrice(), entity.getQtyOnHand(), entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during update.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<InventoryEntity> findAll() {
        List<InventoryEntity> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                InventoryEntity entity = new InventoryEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("productId"),
                        resultSet.getInt("supplierId"),
                        resultSet.getDouble("sellingPrice"),
                        resultSet.getDouble("inventoryPrice"),
                        resultSet.getInt("qtyOnHand")
                );
                inventoryList.add(entity);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return inventoryList;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Inventory WHERE id = ?";
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
        String sql = "SELECT id FROM Inventory";
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
    public InventoryEntity getItem(Integer id) {
        String sql = "SELECT * FROM Inventory WHERE id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new InventoryEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("productId"),
                        resultSet.getInt("supplierId"),
                        resultSet.getDouble("sellingPrice"),
                        resultSet.getDouble("inventoryPrice"),
                        resultSet.getInt("qtyOnHand")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }
}