package icet.edu.erp.dao.custom;

import icet.edu.erp.dao.CrudRepository;
import icet.edu.erp.entity.UserEntity;

public interface UserDao extends CrudRepository<UserEntity> {
    UserEntity getItemByEmail(String email);
    boolean updatePassword(Integer id, String newPwd);
}
