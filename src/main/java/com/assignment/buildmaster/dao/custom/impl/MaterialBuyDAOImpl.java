package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialBuyDAO;
import com.assignment.buildmaster.dto.MaterialBuyDto;
import com.assignment.buildmaster.dao.SQLUtil;

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
    public MaterialBuyDto findById(String selectedId) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    public List<MaterialBuyDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialBuy");
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


    public boolean save(MaterialBuyDto materialBuyDto) throws SQLException {
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
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isMaterialBuySaved = SQLUtil.execute(
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


            boolean isMaterialUpdated = SQLUtil.execute(
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

    public boolean update(MaterialBuyDto materialBuyDto) throws SQLException {
        return SQLUtil.execute(
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

    public boolean delete(String paymentId) throws SQLException {
//        String sql = "DELETE FROM MaterialBuy WHERE Payment_ID=?";
//        return CrudUtil.execute(sql, paymentId);

        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            ResultSet resultSet = SQLUtil.execute(
                    "SELECT Material_ID, Quantity FROM MaterialBuy WHERE Payment_ID = ?",
                    paymentId
            );

            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }

            String materialId = resultSet.getString("Material_ID");
            int quantity = Integer.parseInt(resultSet.getString("Quantity"));

            boolean isPurchaseDeleted = SQLUtil.execute(
                    "DELETE FROM MaterialBuy WHERE Payment_ID = ?",
                    paymentId
            );

            if (!isPurchaseDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = SQLUtil.execute(
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
