package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialUsageDAO;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.dao.SQLUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialUsageDAOImpl implements MaterialUsageDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Usage_ID from MaterialUsage order by Usage_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    @Override
    public MaterialUsageDto findById(String selectedId) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    public boolean save(MaterialUsageDto materialUsageDto) throws SQLException {
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
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            ResultSet resultSet = SQLUtil.execute(
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


            boolean isMaterialUsageSaved = SQLUtil.execute(
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


            boolean isMaterialUpdated = SQLUtil.execute(
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

    public boolean update(MaterialUsageDto materialUsageDto) throws SQLException {
        return SQLUtil.execute(
                "update MaterialUsage set Project_ID=?, Material_ID=?, Quantity_used=?, Date=? where Usage_ID=?",
                materialUsageDto.getProjectId(),
                materialUsageDto.getMaterialId(),
                materialUsageDto.getQuantityUsed(),
                materialUsageDto.getDate(),
                materialUsageDto.getUsageId()
        );
    }

    public List<MaterialUsageDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialUsage");
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


    public boolean delete(String usageId) throws SQLException {
//        String sql = "DELETE FROM MaterialUsage WHERE Usage_ID=?";
//        return CrudUtil.execute(sql, usageId);
        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            ResultSet resultSet = SQLUtil.execute(
                    "SELECT Material_ID, Quantity_used FROM MaterialUsage WHERE Usage_ID = ?",
                    usageId
            );

            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }

            String materialId = resultSet.getString("Material_ID");
            int quantityUsed = Integer.parseInt(resultSet.getString("Quantity_used"));

            boolean isUsageDeleted = SQLUtil.execute(
                    "DELETE FROM MaterialUsage WHERE Usage_ID = ?",
                    usageId
            );

            if (!isUsageDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = SQLUtil.execute(
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
