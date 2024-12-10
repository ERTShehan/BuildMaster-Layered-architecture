package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.MaterialUsageDto;
import com.assignment.buildmasternew.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialUsageModel {
    public String getNextUsageId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Usage_ID from MaterialUsage order by Usage_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    public boolean saveMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
//        return CrudUtil.execute(
//                "insert into MaterialUsage values (?,?,?,?,?)",
//                materialUsageDto.getUsageId(),
//                materialUsageDto.getProjectId(),
//                materialUsageDto.getMaterialId(),
//                materialUsageDto.getQuantityUsed(),
//                materialUsageDto.getDate()
//        );
        Connection connection = null;
        try {
            connection = com.assignment.buildmasternew.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT Quantity_in_Stock FROM Material WHERE Material_ID = ?",
                    materialUsageDto.getMaterialId()
            );

            if (!resultSet.next()) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Material not found!").show();
                return false;
            }

            int currentStock = Integer.parseInt(resultSet.getString("Quantity_in_Stock"));
            int usageQuantity = Integer.parseInt(materialUsageDto.getQuantityUsed());

            if (currentStock < usageQuantity) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Insufficient stock for usage!").show();
                return false;
            }


            boolean isMaterialUsageSaved = CrudUtil.execute(
                    "INSERT INTO MaterialUsage VALUES (?,?,?,?,?)",
                    materialUsageDto.getUsageId(),
                    materialUsageDto.getProjectId(),
                    materialUsageDto.getMaterialId(),
                    materialUsageDto.getQuantityUsed(),
                    materialUsageDto.getDate()
            );

            if (!isMaterialUsageSaved) {
                connection.rollback();
                return false;
            }


            boolean isMaterialUpdated = CrudUtil.execute(
                    "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock - ? WHERE Material_ID = ?",
                    materialUsageDto.getQuantityUsed(),
                    materialUsageDto.getMaterialId()
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

    public boolean updateMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        return CrudUtil.execute(
                "update MaterialUsage set Project_ID=?, Material_ID=?, Quantity_used=?, Date=? where Usage_ID=?",
                materialUsageDto.getProjectId(),
                materialUsageDto.getMaterialId(),
                materialUsageDto.getQuantityUsed(),
                materialUsageDto.getDate(),
                materialUsageDto.getUsageId()
        );
    }

    public List<MaterialUsageDto> getAllUsages() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM MaterialUsage");
        List<MaterialUsageDto> usageList = new ArrayList<>();

        while (rst.next()) {
            usageList.add(new MaterialUsageDto(
                    rst.getString("Usage_ID"),
                    rst.getString("Project_ID"),
                    rst.getString("Material_ID"),
                    rst.getString("Quantity_used"),
                    rst.getString("Date")
            ));
        }
        return usageList;
    }

    public boolean deleteUsage(String usageId) throws SQLException {
//        String sql = "DELETE FROM MaterialUsage WHERE Usage_ID=?";
//        return CrudUtil.execute(sql, usageId);
        Connection connection = null;
        try {
            connection = com.assignment.buildmasternew.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT Material_ID, Quantity_used FROM MaterialUsage WHERE Usage_ID = ?",
                    usageId
            );

            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }

            String materialId = resultSet.getString("Material_ID");
            int quantityUsed = Integer.parseInt(resultSet.getString("Quantity_used"));

            boolean isUsageDeleted = CrudUtil.execute(
                    "DELETE FROM MaterialUsage WHERE Usage_ID = ?",
                    usageId
            );

            if (!isUsageDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = CrudUtil.execute(
                    "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock + ? WHERE Material_ID = ?",
                    quantityUsed,
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
