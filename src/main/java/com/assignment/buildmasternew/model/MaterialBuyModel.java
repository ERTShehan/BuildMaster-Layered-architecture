package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.MaterialBuyDto;
import com.assignment.buildmasternew.dto.MaterialUsageDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBuyModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Payment_ID from MaterialBuy order by Payment_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

    public List<MaterialBuyDto> getAllPurchases() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM MaterialBuy");
        List<MaterialBuyDto> purchaseList = new ArrayList<>();
        while (rst.next()) {
            purchaseList.add(new MaterialBuyDto(
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

    public boolean saveMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
//        return CrudUtil.execute(
//                "insert into MaterialBuy values (?,?,?,?,?,?,?)",
//                materialBuyDto.getPaymentId(),
//                materialBuyDto.getMaterialId(),
//                materialBuyDto.getSupplierId(),
//                materialBuyDto.getDate(),
//                materialBuyDto.getUnitAmount(),
//                materialBuyDto.getQuantity(),
//                materialBuyDto.getTotalPrice()
//        );

        Connection connection = null;
        try {
            connection = com.assignment.buildmasternew.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isMaterialBuySaved = CrudUtil.execute(
                    "INSERT INTO MaterialBuy VALUES (?,?,?,?,?,?,?)",
                    materialBuyDto.getPaymentId(),
                    materialBuyDto.getMaterialId(),
                    materialBuyDto.getSupplierId(),
                    materialBuyDto.getDate(),
                    materialBuyDto.getUnitAmount(),
                    materialBuyDto.getQuantity(),
                    materialBuyDto.getTotalPrice()
            );

            if (!isMaterialBuySaved) {
                connection.rollback();
                return false;
            }


            boolean isMaterialUpdated = CrudUtil.execute(
                    "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock + ? WHERE Material_ID = ?",
                    materialBuyDto.getQuantity(),
                    materialBuyDto.getMaterialId()
            );

            if (!isMaterialUpdated) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    public boolean updateMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        return CrudUtil.execute(
                "update MaterialBuy set Material_ID=?, Supplier_ID=?, Date=?, Unit_Amount=?, Quantity=?, Total_Price=? where Payment_ID=?",
                materialBuyDto.getMaterialId(),
                materialBuyDto.getSupplierId(),
                materialBuyDto.getDate(),
                materialBuyDto.getUnitAmount(),
                materialBuyDto.getQuantity(),
                materialBuyDto.getTotalPrice(),
                materialBuyDto.getPaymentId()
        );
    }

    public boolean deletePurchase(String paymentId) throws SQLException {
//        String sql = "DELETE FROM MaterialBuy WHERE Payment_ID=?";
//        return CrudUtil.execute(sql, paymentId);

        Connection connection = null;
        try {
            connection = com.assignment.buildmasternew.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT Material_ID, Quantity FROM MaterialBuy WHERE Payment_ID = ?",
                    paymentId
            );

            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }

            String materialId = resultSet.getString("Material_ID");
            int quantity = Integer.parseInt(resultSet.getString("Quantity"));

            boolean isPurchaseDeleted = CrudUtil.execute(
                    "DELETE FROM MaterialBuy WHERE Payment_ID = ?",
                    paymentId
            );

            if (!isPurchaseDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = CrudUtil.execute(
                    "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock - ? WHERE Material_ID = ?",
                    quantity,
                    materialId
            );

            if (!isMaterialUpdated) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }
}
