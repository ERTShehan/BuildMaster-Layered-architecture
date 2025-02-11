package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialUsageBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.dao.custom.impl.MaterialUsageDAOImpl;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.entity.MaterialUsage;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialUsageBOImpl implements MaterialUsageBO {
    MaterialUsageDAOImpl materialUsageDAO = (MaterialUsageDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALUSAGE);
//    MaterialUsageBOImpl materialUsageBO = (MaterialUsageBOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALUSAGE);

    public String getNextMaterialUsageId() throws SQLException {
        return materialUsageDAO.getNextId();
    }

    public MaterialUsageDto findByMaterialUsageId(String selectedId) throws SQLException {
        MaterialUsage materialUsage = materialUsageDAO.findById(selectedId);
        return new MaterialUsageDto(materialUsage.getUsageId(), materialUsage.getProjectId(), materialUsage.getMaterialId(), materialUsage.getQuantityUsed(), materialUsage.getDate());
    }

    public ArrayList<String> getAllMaterialUsageIds() throws SQLException {
        return materialUsageDAO.getAllIds();
    }

    public boolean saveMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
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
//        return materialUsageDAO.save(new MaterialUsage(materialUsageDto.getUsageId(), materialUsageDto.getProjectId(), materialUsageDto.getMaterialId(), materialUsageDto.getQuantityUsed(), materialUsageDto.getDate()));
    }

    public boolean updateMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        return materialUsageDAO.update(new MaterialUsage(materialUsageDto.getUsageId(), materialUsageDto.getProjectId(), materialUsageDto.getMaterialId(), materialUsageDto.getQuantityUsed(), materialUsageDto.getDate()));
    }

    public List<MaterialUsageDto> getAllMaterialUsage() throws SQLException {
        List<MaterialUsage> all = materialUsageDAO.getAll();
        List<MaterialUsageDto> allMaterialUsage = new ArrayList<>();
        for (MaterialUsage materialUsage : all) {
            allMaterialUsage.add(new MaterialUsageDto(materialUsage.getUsageId(), materialUsage.getProjectId(), materialUsage.getMaterialId(), materialUsage.getQuantityUsed(), materialUsage.getDate()));
        }
        return allMaterialUsage;
    }

    public boolean deleteMaterialUsage(String usageId) throws SQLException {
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
//        return materialUsageDAO.delete(usageId);
    }
}
