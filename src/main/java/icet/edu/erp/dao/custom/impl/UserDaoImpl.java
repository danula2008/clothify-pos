package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.UserDao;
import icet.edu.erp.entity.UserEntity;
import icet.edu.erp.util.CrudUtil;
import icet.edu.erp.util.ShowAlert;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean save(UserEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO User (name, email, password, role, lastLogin, initialLogin) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getLastLogin(),
                    entity.getInitialLogin());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during save.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public boolean update(UserEntity entity) throws SQLIntegrityConstraintViolationException {
        String sql = "UPDATE User SET name = ?, email = ?, password = ?, role = ?, lastLogin = ?, initialLogin = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getLastLogin(),
                    entity.getInitialLogin(),
                    entity.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException("Integrity constraint violation during update.");
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                UserEntity entity = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("lastLogin"),
                        resultSet.getTimestamp("initialLogin")
                );
                userList.add(entity);
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return userList;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM User WHERE id = ?";
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
        String sql = "SELECT id FROM User";
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
    public UserEntity getItem(Integer id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("lastLogin"),
                        resultSet.getTimestamp("initialLogin")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null;
    }

    public UserEntity getItemByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, email);
            if (resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getTimestamp("lastLogin"),
                        resultSet.getTimestamp("initialLogin")
                );
            }
        } catch (SQLException e) {
            ShowAlert.databaseError();
        }
        return null; // Return null if no user is found or an error occurs
    }

    @Override
    public boolean updatePassword(Integer id, String newPwd) {
        String sql = "UPDATE User SET password = ? WHERE id = ?";
        try {
            return CrudUtil.execute(sql, newPwd, id);
        } catch (SQLException e) {
            ShowAlert.databaseError();
            return false;
        }
    }
}
