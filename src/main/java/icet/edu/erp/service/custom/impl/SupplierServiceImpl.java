package icet.edu.erp.service.custom.impl;

import com.jfoenix.controls.JFXTextField;
import icet.edu.erp.dto.Supplier;
import icet.edu.erp.service.custom.SupplierService;
import javafx.collections.ObservableList;

import java.sql.SQLIntegrityConstraintViolationException;

public class SupplierServiceImpl implements SupplierService {
    private static SupplierServiceImpl instance;
    private SupplierServiceImpl(){}
    public static SupplierServiceImpl getInstance(){
        return instance == null? instance = new SupplierServiceImpl() : instance;
    }

    @Override
    public boolean deleteSupplier(Integer id) {
        return false;
    }

    @Override
    public ObservableList<Supplier> getAllCustomers() {
        return null;
    }

    @Override
    public Integer getId(JFXTextField txtSupplierId) {
        return null;
    }

    @Override
    public Supplier getSupplier(JFXTextField txtProductId) {
        return null;
    }

    @Override
    public boolean addSupplier(Supplier supplier) throws SQLIntegrityConstraintViolationException {
        return false;
    }
}
