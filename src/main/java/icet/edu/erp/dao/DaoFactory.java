package icet.edu.erp.dao;

import icet.edu.erp.dao.custom.impl.UserDaoImpl;
import icet.edu.erp.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null? instance=new DaoFactory() : instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        return (T) switch (type){
            case CUSTOMER -> null;
            case EMPLOYEE -> null;
            case INVENTORY -> null;
            case ORDER -> null;
            case PRODUCT -> null;
            case SUPPLIER -> null;
            case USER -> UserDaoImpl.getInstance();
        };
    }
}
