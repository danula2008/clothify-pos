package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.EmployeeDao;
import icet.edu.erp.entity.EmployeeEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private static EmployeeDaoImpl instance;

    private EmployeeDaoImpl(){}

    public static EmployeeDaoImpl getInstance() {
        return instance==null? instance = new EmployeeDaoImpl() : instance;
    }

    @Override
    public boolean save(EmployeeEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Employee (userId, gender, phoneNo, hireDate, dob, salary) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            return CrudUtil.execute(sql,
                    entity.getUserId(),
                    entity.getGender(),
                    entity.getPhoneNo(),
                    entity.getHireDate(),
                    entity.getDob(),
                    entity.getSalary());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(EmployeeEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE Employee SET userId = ?, gender = ?, phoneNo = ?, hireDate = ?, dob = ?, salary = ? WHERE id = ?";

        try {
            return CrudUtil.execute(sql,
                    entity.getUserId(),
                    entity.getGender(),
                    entity.getPhoneNo(),
                    entity.getHireDate(),
                    entity.getDob(),
                    entity.getSalary(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (ResultSet rs = CrudUtil.execute(sql)) {
            while (rs.next()) {
                EmployeeEntity employee = new EmployeeEntity(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("gender"),
                        rs.getString("phoneNo"),
                        rs.getDate("hireDate").toLocalDate(),
                        rs.getDate("dob").toLocalDate(),
                        rs.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }

        return employees;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Employee WHERE id = ?";

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
        String sql = "SELECT id FROM Employee";

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
    public EmployeeEntity getItem(Integer id) {
        String sql = "SELECT * FROM Employee WHERE id = ?";

        try (ResultSet rs = CrudUtil.execute(sql, id)) {
            if (rs.next()) {
                return new EmployeeEntity(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("gender"),
                        rs.getString("phoneNo"),
                        rs.getDate("hireDate").toLocalDate(),
                        rs.getDate("dob").toLocalDate(),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }

    @Override
    public EmployeeEntity getItemByUserId(Integer userId) {
        String sql = "SELECT * FROM Employee WHERE userId = ?";

        try (ResultSet rs = CrudUtil.execute(sql, userId)) {
            if (rs.next()) {
                return new EmployeeEntity(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("gender"),
                        rs.getString("phoneNo"),
                        rs.getDate("hireDate").toLocalDate(),
                        rs.getDate("dob").toLocalDate(),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }
}
