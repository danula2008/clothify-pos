package icet.edu.erp.dao.custom.impl;

import icet.edu.erp.dao.custom.UserDao;
import icet.edu.erp.entity.UserEntity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;
    private UserDaoImpl(){}

    public static UserDaoImpl getInstance() {
        return instance==null? instance = new UserDaoImpl() : instance;
    }

    @Override
    public boolean save(UserEntity entity) throws SQLIntegrityConstraintViolationException {
        return false;
    }

    @Override
    public boolean update(UserEntity entity) throws SQLIntegrityConstraintViolationException {
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<String> getIDs() {
        return null;
    }

    @Override
    public UserEntity getItem(String id) {
        return null;
    }
}
