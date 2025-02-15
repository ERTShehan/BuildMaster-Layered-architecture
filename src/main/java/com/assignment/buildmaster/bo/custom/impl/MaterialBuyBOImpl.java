package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialBuyBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.MaterialBuyDAO;
import com.assignment.buildmaster.dto.MaterialBuyDto;
import com.assignment.buildmaster.entity.MaterialBuy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBuyBOImpl implements MaterialBuyBO {
    private final MaterialBuyDAO materialBuyDAO = (MaterialBuyDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALBUY);

    @Override
    public String getNextMaterialBuyId() throws SQLException {
        return materialBuyDAO.getNextId();
    }

    @Override
    public List<MaterialBuyDto> getAllMaterialBuy() throws SQLException {
        List<MaterialBuy> all = materialBuyDAO.getAll();
        List<MaterialBuyDto> allMaterialBuy = new ArrayList<>();
        for (MaterialBuy materialBuy : all) {
            allMaterialBuy.add(new MaterialBuyDto(
                    materialBuy.getPaymentId(),
                    materialBuy.getMaterialId(),
                    materialBuy.getSupplierId(),
                    materialBuy.getDate(),
                    materialBuy.getUnitAmount(),
                    materialBuy.getQuantity(),
                    materialBuy.getTotalPrice()
            ));
        }
        return allMaterialBuy;
    }

    @Override
    public boolean saveMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            MaterialBuy materialBuy = new MaterialBuy(
                    materialBuyDto.getPaymentId(),
                    materialBuyDto.getMaterialId(),
                    materialBuyDto.getSupplierId(),
                    materialBuyDto.getDate(),
                    materialBuyDto.getUnitAmount(),
                    materialBuyDto.getQuantity(),
                    materialBuyDto.getTotalPrice()
            );

            boolean isMaterialBuySaved = materialBuyDAO.save(materialBuy);
            if (!isMaterialBuySaved) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = materialBuyDAO.updateMaterialStock(materialBuyDto.getMaterialId(), Integer.parseInt(materialBuyDto.getQuantity()));
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
    public boolean updateMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        MaterialBuy materialBuy = new MaterialBuy(
                materialBuyDto.getPaymentId(),
                materialBuyDto.getMaterialId(),
                materialBuyDto.getSupplierId(),
                materialBuyDto.getDate(),
                materialBuyDto.getUnitAmount(),
                materialBuyDto.getQuantity(),
                materialBuyDto.getTotalPrice()
        );
        return materialBuyDAO.update(materialBuy);
    }

    @Override
    public boolean deleteMaterialBuy(String paymentId) throws SQLException {
        Connection connection = null;
        try {
            connection = com.assignment.buildmaster.db.DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            MaterialBuy materialBuy = materialBuyDAO.findById(paymentId);
            if (materialBuy == null) {
                connection.rollback();
                return false;
            }

            boolean isPurchaseDeleted = materialBuyDAO.delete(paymentId);
            if (!isPurchaseDeleted) {
                connection.rollback();
                return false;
            }

            boolean isMaterialUpdated = materialBuyDAO.updateMaterialStock(materialBuy.getMaterialId(), -Integer.parseInt(materialBuy.getQuantity()));
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