package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.OrderDao;
import icet.edu.erp.dto.Order;
import icet.edu.erp.service.custom.OrderService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.sql.SQLIntegrityConstraintViolationException;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;
    private OrderServiceImpl(){}
    public static OrderServiceImpl getInstance(){
        return instance==null? instance = new OrderServiceImpl() : instance;
    }

    private final OrderDao repository = DaoFactory.getInstance().getDaoType(DaoType.ORDER);
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public ObservableList<Order> getAllCustomers() {
        return (ObservableList<Order>) repository.findAll().stream().map(orderEntity -> mapper.map(orderEntity, Order.class)).toList();
    }

    @Override
    public boolean placeOrder(Order cash) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
