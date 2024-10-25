package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.CustomerDao;
import icet.edu.erp.entity.CustomerEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private CustomerDaoImpl(){}
    private static CustomerDaoImpl instance;

    public static CustomerDaoImpl getInstance() {
        return instance==null? instance = new CustomerDaoImpl() : instance;
    }

    @Override
    public boolean save(CustomerEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Customer (name, gender, email, phoneNo, dob, joinedDate, loyaltyTier) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getGender(),
                    entity.getEmail(),
                    entity.getPhoneNo(),
                    entity.getDob(),
                    entity.getJoinedDate(),
                    entity.getLoyaltyTier());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }


    @Override
    public boolean update(CustomerEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE Customer SET name = ?, gender = ?, email = ?, phoneNo = ?, dob = ?, joinedDate = ?, loyaltyTier = ? WHERE id = ?";

        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getGender(),
                    entity.getEmail(),
                    entity.getPhoneNo(),
                    entity.getDob(),
                    entity.getJoinedDate(),
                    entity.getLoyaltyTier(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }


    @Override
    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (ResultSet rs = CrudUtil.execute(sql)) {
            while (rs.next()) {
                CustomerEntity customer = new CustomerEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phoneNo"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getDate("joinedDate").toLocalDate(),
                        rs.getString("loyaltyTier")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return customers;
    }


    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Customer WHERE id = ?";

        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }


    @Override
    public List<Integer> getIDs() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM Customer";

        try (ResultSet rs = CrudUtil.execute(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return ids;
    }


    @Override
    public CustomerEntity getItem(Integer id) {
        String sql = "SELECT * FROM Customer WHERE id = ?";

        try (ResultSet rs = CrudUtil.execute(sql, id)) {
            if (rs.next()) {
                return new CustomerEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phoneNo"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getDate("joinedDate").toLocalDate(),
                        rs.getString("loyaltyTier")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }

    @Override
    public LocalDate getDate(Integer id) {
        String sql = "SELECT dob FROM Customer WHERE id = ?";

        try (ResultSet rs = CrudUtil.execute(sql, id)) {
            if (rs.next()) {
                return rs.getDate("dob").toLocalDate();
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }

}
