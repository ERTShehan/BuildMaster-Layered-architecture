package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.SupplierBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.SupplierDAOImpl;
import com.assignment.buildmaster.dto.SupplierDto;
import com.assignment.buildmaster.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAOImpl supplierDAO = (SupplierDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
//    SupplierDAOImpl supplierDAO = new SupplierDAOImpl();

    public String getNextSupplierId() throws SQLException {
        return supplierDAO.getNextId();
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException {
        return supplierDAO.getAllIds();
    }

    public SupplierDto findBySupplierId(String selectedSupplierId) throws SQLException {
        Supplier supplier = supplierDAO.findById(selectedSupplierId);
        return new SupplierDto(supplier.getSupplierId(), supplier.getName(), supplier.getAddress(), supplier.getPhoneNo(), supplier.getEmail());
    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        return supplierDAO.save(new Supplier(supplierDto.getSupplierId(), supplierDto.getName(), supplierDto.getAddress(), supplierDto.getPhoneNo(), supplierDto.getEmail()));
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return supplierDAO.update(new Supplier(supplierDto.getSupplierId(), supplierDto.getName(), supplierDto.getAddress(), supplierDto.getPhoneNo(), supplierDto.getEmail()));
    }

    public boolean deleteSupplier(String supplierID) throws SQLException {
        return supplierDAO.delete(supplierID);
    }

    public List<SupplierDto> getAllSupplier() throws SQLException {
        List<Supplier> suppliers = supplierDAO.getAll();
        List<SupplierDto> supplierList = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierList.add(new SupplierDto(supplier.getSupplierId(), supplier.getName(), supplier.getAddress(), supplier.getPhoneNo(), supplier.getEmail()));
        }
        return supplierList;
    }

    public String findNameBySupplierId(String supplierId) throws SQLException {
        return supplierDAO.findNameById(supplierId);
    }
}
