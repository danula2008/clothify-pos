package icet.edu.erp.service.custom.impl;

import icet.edu.erp.dao.DaoFactory;
import icet.edu.erp.dao.custom.SupplierDao;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.entity.SupplierEntity;
import icet.edu.erp.service.custom.SupplierService;
import icet.edu.erp.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.sql.SQLIntegrityConstraintViolationException;

public class SupplierServiceImpl implements SupplierService {
    private static SupplierServiceImpl instance;
    private SupplierServiceImpl(){}
    public static SupplierServiceImpl getInstance(){
        return instance == null? instance = new SupplierServiceImpl() : instance;
    }

    private final SupplierDao repository = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public boolean deleteSupplier(Integer id) {
        return repository.delete(id);
    }

    @Override
    public ObservableList<Supplier> getAllCustomers() {
        return (ObservableList<Supplier>) repository.findAll().stream().map(supplierEntity -> mapper.map(supplierEntity, Supplier.class)).toList();
    }

    @Override
    public Supplier getSupplier(Integer supplierId) {
        return mapper.map(repository.getItem(supplierId), Supplier.class);
    }

    @Override
    public boolean addSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException {
        return repository.save(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public boolean hasId(int supplierId) {
        return repository.getItem(supplierId) != null;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException {
        return repository.update(mapper.map(supplier, SupplierEntity.class));
    }
}
