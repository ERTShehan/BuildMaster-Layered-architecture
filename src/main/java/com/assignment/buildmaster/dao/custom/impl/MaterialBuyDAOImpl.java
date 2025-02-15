package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialBuyDAO;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.MaterialBuy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBuyDAOImpl implements MaterialBuyDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Payment_ID FROM MaterialBuy ORDER BY Payment_ID DESC LIMIT 1");
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
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialBuy WHERE Payment_ID = ?", selectedId);
        if (rst.next()) {
            return new MaterialBuy(
                    rst.getString("Payment_ID"),
                    rst.getString("Material_ID"),
                    rst.getString("Supplier_ID"),
                    rst.getDate("Date"),
                    rst.getDouble("Unit_Amount"),
                    rst.getString("Quantity"),
                    rst.getDouble("Total_Price")
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Payment_ID FROM MaterialBuy");
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }

    @Override
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

    @Override
    public boolean save(MaterialBuy entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO MaterialBuy VALUES (?,?,?,?,?,?,?)",
                entity.getPaymentId(),
                entity.getMaterialId(),
                entity.getSupplierId(),
                entity.getDate(),
                entity.getUnitAmount(),
                entity.getQuantity(),
                entity.getTotalPrice()
        );
    }

    @Override
    public boolean update(MaterialBuy entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE MaterialBuy SET Material_ID=?, Supplier_ID=?, Date=?, Unit_Amount=?, Quantity=?, Total_Price=? WHERE Payment_ID=?",
                entity.getMaterialId(),
                entity.getSupplierId(),
                entity.getDate(),
                entity.getUnitAmount(),
                entity.getQuantity(),
                entity.getTotalPrice(),
                entity.getPaymentId()
        );
    }

    @Override
    public boolean delete(String paymentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM MaterialBuy WHERE Payment_ID=?", paymentId);
    }

    @Override
    public boolean updateMaterialStock(String materialId, int quantity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock + ? WHERE Material_ID = ?",
                quantity,
                materialId
        );
    }
}