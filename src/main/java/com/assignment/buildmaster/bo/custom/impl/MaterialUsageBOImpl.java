package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialUsageBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.MaterialUsageDAO;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.entity.MaterialUsage;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialUsageBOImpl implements MaterialUsageBO {
    private final MaterialUsageDAO materialUsageDAO = (MaterialUsageDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALUSAGE);

    @Override
    public String getNextMaterialUsageId() throws SQLException {
        return materialUsageDAO.getNextId();
    }

    @Override
    public MaterialUsageDto findByMaterialUsageId(String selectedId) throws SQLException {
        MaterialUsage materialUsage = materialUsageDAO.findById(selectedId);
        return new MaterialUsageDto(materialUsage.getUsageId(), materialUsage.getProjectId(), materialUsage.getMaterialId(), materialUsage.getQuantityUsed(), materialUsage.getDate());
    }

    @Override
    public ArrayList<String> getAllMaterialUsageIds() throws SQLException {
        return materialUsageDAO.getAllIds();
    }

    @Override
    public boolean saveMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            int currentStock = materialUsageDAO.getMaterialStock(materialUsageDto.getMaterialId());
            int usageQuantity = Integer.parseInt(materialUsageDto.getQuantityUsed());

            if (currentStock < usageQuantity) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Insufficient stock for usage!").show();
                return false;
            }

            MaterialUsage materialUsage = new MaterialUsage(
                    materialUsageDto.getUsageId(),
                    materialUsageDto.getProjectId(),
                    materialUsageDto.getMaterialId(),
                    materialUsageDto.getQuantityUsed(),
                    materialUsageDto.getDate()
            );

            boolean isMaterialUsageSaved = materialUsageDAO.save(materialUsage);

            if (!isMaterialUsageSaved) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = materialUsageDAO.updateMaterialStock(materialUsageDto.getMaterialId(), -usageQuantity);

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

    @Override
    public boolean updateMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        MaterialUsage materialUsage = new MaterialUsage(
                materialUsageDto.getUsageId(),
                materialUsageDto.getProjectId(),
                materialUsageDto.getMaterialId(),
                materialUsageDto.getQuantityUsed(),
                materialUsageDto.getDate()
        );
        return materialUsageDAO.update(materialUsage);
    }

    @Override
    public List<MaterialUsageDto> getAllMaterialUsage() throws SQLException {
        List<MaterialUsage> all = materialUsageDAO.getAll();
        List<MaterialUsageDto> allMaterialUsage = new ArrayList<>();
        for (MaterialUsage materialUsage : all) {
            allMaterialUsage.add(new MaterialUsageDto(
                    materialUsage.getUsageId(),
                    materialUsage.getProjectId(),
                    materialUsage.getMaterialId(),
                    materialUsage.getQuantityUsed(),
                    materialUsage.getDate()
            ));
        }
        return allMaterialUsage;
    }

    @Override
    public boolean deleteMaterialUsage(String usageId) throws SQLException {
        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            MaterialUsage materialUsage = materialUsageDAO.findById(usageId);
            if (materialUsage == null) {
                connection.rollback();
                return false;
            }

            int quantityUsed = Integer.parseInt(materialUsage.getQuantityUsed());
            String materialId = materialUsage.getMaterialId();

            boolean isUsageDeleted = materialUsageDAO.delete(usageId);
            if (!isUsageDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = materialUsageDAO.updateMaterialStock(materialId, quantityUsed);
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