package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.OrderDao;
import icet.edu.erp.entity.OrderEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl instance;

    private OrderDaoImpl(){}

    public static OrderDaoImpl getInstance() {
        return instance==null? instance = new OrderDaoImpl() : instance;
    }

    @Override
    public boolean save(OrderEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO `Order` (CustId, EmpId, dateTime, paymentType, totalDiscount, totalCost, returnStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getCustId(),
                    entity.getEmpId(),
                    entity.getDateTime(),
                    entity.getPaymentType(),
                    entity.getTotalDiscount(),
                    entity.getTotalCost(),
                    entity.getReturnStatus());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during save.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(OrderEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE `Order` SET CustId = ?, EmpId = ?, dateTime = ?, paymentType = ?, totalDiscount = ?, totalCost = ?, returnStatus = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql,
                    entity.getCustId(),
                    entity.getEmpId(),
                    entity.getDateTime(),
                    entity.getPaymentType(),
                    entity.getTotalDiscount(),
                    entity.getTotalCost(),
                    entity.getReturnStatus(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during update.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<OrderEntity> findAll() {
        List<OrderEntity> orderList = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                OrderEntity entity = new OrderEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("CustId"),
                        resultSet.getInt("EmpId"),
                        resultSet.getTimestamp("dateTime"),
                        resultSet.getString("paymentType"),
                        resultSet.getDouble("totalDiscount"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("returnStatus")
                );
                orderList.add(entity);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return orderList;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM `Order` WHERE id = ?";
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
        String sql = "SELECT id FROM `Order`";
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
    public OrderEntity getItem(Integer id) {
        String sql = "SELECT * FROM `Order` WHERE id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new OrderEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("CustId"),
                        resultSet.getInt("EmpId"),
                        resultSet.getTimestamp("dateTime"),
                        resultSet.getString("paymentType"),
                        resultSet.getDouble("totalDiscount"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("returnStatus")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }
}