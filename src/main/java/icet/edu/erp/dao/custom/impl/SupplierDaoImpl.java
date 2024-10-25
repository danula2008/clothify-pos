package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.SupplierDao;
import icet.edu.erp.entity.SupplierEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
public class SupplierDaoImpl implements SupplierDao {

    private static SupplierDaoImpl instance;

    private SupplierDaoImpl(){}

    public static SupplierDaoImpl getInstance() {
        return instance==null? instance = new SupplierDaoImpl() : instance;
    }


    @Override
    public boolean save(SupplierEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Supplier (name, company, email, contact, pendingPayment, lastOrderDay) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getCompany(),
                    entity.getEmail(),
                    entity.getContact(),
                    entity.getPendingPayment(),
                    entity.getLastOrderDay());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during save.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(SupplierEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE Supplier SET name = ?, company = ?, email = ?, contact = ?, pendingPayment = ?, lastOrderDay = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getCompany(),
                    entity.getEmail(),
                    entity.getContact(),
                    entity.getPendingPayment(),
                    entity.getLastOrderDay(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during update.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<SupplierEntity> findAll() {
        List<SupplierEntity> supplierList = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                SupplierEntity entity = new SupplierEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("company"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getDouble("pendingPayment"),
                        resultSet.getObject("lastOrderDay", LocalDate.class) // Assuming the database stores this as a date
                );
                supplierList.add(entity);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return supplierList;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Supplier WHERE id = ?";
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
        String sql = "SELECT id FROM Supplier";
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
    public SupplierEntity getItem(Integer id) {
        String sql = "SELECT * FROM Supplier WHERE id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new SupplierEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("company"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getDouble("pendingPayment"),
                        resultSet.getObject("lastOrderDay", LocalDate.class)
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }
}
