package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialBuyDAO;
import com.assignment.buildmaster.dto.MaterialBuyDto;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.MaterialBuy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBuyDAOImpl implements MaterialBuyDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Payment_ID from MaterialBuy order by Payment_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

    @Override
    public MaterialBuy findById(String selectedId) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    public List<MaterialBuy> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialBuy");
        List<MaterialBuy> purchaseList = new ArrayList<>();
        while (rst.next()) {
            purchaseList.add(new MaterialBuy(
                    rst.getString("Payment_ID"),
                    rst.getString("Material_ID"),
                    rst.getString("Supplier_ID"),
                    rst.getDate("Date"),
                    rst.getDouble("Unit_Amount"),
                    rst.getString("Quantity"),
                    rst.getDouble("Total_Price")
            ));
        }
        return purchaseList;
    }


    public boolean save(MaterialBuy entity) throws SQLException {
        return SQLUtil.execute(
                "insert into MaterialBuy values (?,?,?,?,?,?,?)",
                entity.getPaymentId(),
                entity.getMaterialId(),
                entity.getSupplierId(),
                entity.getDate(),
                entity.getUnitAmount(),
                entity.getQuantity(),
                entity.getTotalPrice()
        );
    }

    public boolean update(MaterialBuy entity) throws SQLException {
        return SQLUtil.execute(
                "update MaterialBuy set Material_ID=?, Supplier_ID=?, Date=?, Unit_Amount=?, Quantity=?, Total_Price=? where Payment_ID=?",
                entity.getMaterialId(),
                entity.getSupplierId(),
                entity.getDate(),
                entity.getUnitAmount(),
                entity.getQuantity(),
                entity.getTotalPrice(),
                entity.getPaymentId()
        );
    }

    public boolean delete(String paymentId) throws SQLException {
        String sql = "DELETE FROM MaterialBuy WHERE Payment_ID=?";
        return SQLUtil.execute(sql, paymentId);
    }
}
