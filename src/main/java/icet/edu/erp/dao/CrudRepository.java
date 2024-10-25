package icet.edu.erp.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface CrudRepository<T> extends SuperDao{
    boolean save(T entity) throws SQLIntegrityConstraintViolationException;
    boolean update(T entity) throws SQLIntegrityConstraintViolationException;
    List<T> findAll();
    boolean delete(Integer id);
    List<Integer> getIDs();
    T getItem(Integer id);
}
