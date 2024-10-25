package icet.edu.erp.service;

import icet.edu.erp.service.custom.impl.*;
import icet.edu.erp.util.ServiceType;

public class ServiceFactory {
    private ServiceFactory(){}
    private static ServiceFactory instance;
    public static ServiceFactory getInstance(){
        return instance==null? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        return (T) switch (type){
            case CUSTOMER -> CustomerServiceImpl.getInstance();
            case EMPLOYEE -> EmployeeServiceImpl.getInstance();
            case INVENTORY -> InventoryServiceImpl.getInstance();
            case ORDER -> OrderServiceImpl.getInstance();
            case PRODUCT -> ProductServiceImpl.getInstance();
            case SUPPLIER -> SupplierServiceImpl.getInstance();
            case USER -> UserServiceImpl.getInstance();
        };
    }
}
