package icet.edu.erp.dao;

import icet.edu.erp.dao.custom.impl.*;
import icet.edu.erp.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null? instance=new DaoFactory() : instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        return (T) switch (type){
            case CUSTOMER -> CustomerDaoImpl.getInstance();
            case EMPLOYEE -> EmployeeDaoImpl.getInstance();
            case INVENTORY -> InventoryDaoImpl.getInstance();
            case ORDER -> OrderDaoImpl.getInstance();
            case PRODUCT -> ProductDaoImpl.getInstance();
            case SUPPLIER -> SupplierDaoImpl.getInstance();
            case USER -> UserDaoImpl.getInstance();
        };
    }
}
