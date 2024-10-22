package icet.edu.erp.service;

import icet.edu.erp.service.custom.impl.UserServiceImpl;
import icet.edu.erp.util.ServiceType;

public class ServiceFactory {
    private ServiceFactory(){}
    private static ServiceFactory instance;
    public static ServiceFactory getInstance(){
        return instance==null? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        return (T) switch (type){
            case CUSTOMER -> null;
            case EMPLOYEE -> null;
            case INVENTORY -> null;
            case ORDER -> null;
            case PRODUCT -> null;
            case SUPPLIER -> null;
            case USER -> UserServiceImpl.getInstance();
        };
    }
}
