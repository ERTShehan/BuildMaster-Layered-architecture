package com.assignment.buildmaster.dao.custom.Impl;

import com.assignment.buildmaster.dao.custom.SupplierDAO;
import com.assignment.buildmaster.dto.SupplierDto;
import com.assignment.buildmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Supplier_ID from Supplier order by Supplier_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Supplier_ID from Supplier");
        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()){
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public SupplierDto findById(String selectedSupplierId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Supplier where Supplier_ID=?", selectedSupplierId);
        if (rst.next()) {
            return new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean save(SupplierDto supplierDto) throws SQLException {
        return SQLUtil.execute(
                "insert into Supplier values (?,?,?,?,?)",
                supplierDto.getSupplierId(),
                supplierDto.getName(),
                supplierDto.getAddress(),
                supplierDto.getPhoneNo(),
                supplierDto.getEmail()
        );
    }

    public boolean update(SupplierDto supplierDto) throws SQLException {
        String sql = "update Supplier set Name=?, Address=?, Phone_No=?, Email=? where Supplier_ID=?";
        return SQLUtil.execute(sql, supplierDto.getName(), supplierDto.getAddress(), supplierDto.getPhoneNo(), supplierDto.getEmail(), supplierDto.getSupplierId());
    }

    public boolean delete(String supplierID) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE Supplier_ID=?";
        return SQLUtil.execute(sql, supplierID);
    }

    public List<SupplierDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * from Supplier");
        List<SupplierDto> supplierList = new ArrayList<>();

        while(rst.next()) {
            supplierList.add(new SupplierDto(
                    rst.getString("Supplier_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone_No"),
                    rst.getString("Email")
            ));
        }
        return supplierList;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public String getName(String Id) throws SQLException {
        return "";
    }

    @Override
    public String getInfo(String Id) throws SQLException {
        return "";
    }

    @Override
    public String getUnit(String Id) throws SQLException {
        return "";
    }

    @Override
    public ArrayList<String> findAllIds() throws SQLException {
        return null;
    }

    public String findNameById(String supplierId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Supplier where Supplier_ID=?", supplierId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
        return null;
    }
}
