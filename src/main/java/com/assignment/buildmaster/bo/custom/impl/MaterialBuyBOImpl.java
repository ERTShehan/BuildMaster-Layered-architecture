package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialBuyBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.dao.custom.impl.MaterialBuyDAOImpl;
import com.assignment.buildmaster.dto.MaterialBuyDto;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.entity.MaterialBuy;
import com.assignment.buildmaster.entity.MaterialUsage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBuyBOImpl implements MaterialBuyBO {
    MaterialBuyDAOImpl materialBuyDAO = (MaterialBuyDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALBUY);
//    MaterialBuyDAOImpl materialBuyDAO = new MaterialBuyDAOImpl();

    public String getNextMaterialBuyId() throws SQLException {
        return materialBuyDAO.getNextId();
    }

    public List<MaterialBuyDto> getAllMaterialBuy() throws SQLException {
        List<MaterialBuy> all = materialBuyDAO.getAll();
        List<MaterialBuyDto> allMaterialBuy = new ArrayList<>();
        for (MaterialBuy materialBuy : all) {
            allMaterialBuy.add(new MaterialBuyDto(materialBuy.getPaymentId(), materialBuy.getMaterialId(), materialBuy.getSupplierId(), materialBuy.getDate(), materialBuy.getUnitAmount(), materialBuy.getQuantity(), materialBuy.getTotalPrice()));
        }

        return allMaterialBuy;
    }

    public boolean saveMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
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
//        return materialBuyDAO.save(new MaterialBuy(materialBuyDto.getPaymentId(), materialBuyDto.getMaterialId(), materialBuyDto.getSupplierId(), materialBuyDto.getDate(), materialBuyDto.getUnitAmount(), materialBuyDto.getQuantity(), materialBuyDto.getTotalPrice()));
    }

    public boolean updateMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        return materialBuyDAO.update(new MaterialBuy(materialBuyDto.getPaymentId(), materialBuyDto.getMaterialId(), materialBuyDto.getSupplierId(), materialBuyDto.getDate(), materialBuyDto.getUnitAmount(), materialBuyDto.getQuantity(), materialBuyDto.getTotalPrice()));
    }

    public boolean deleteMaterialBuy(String paymentId) throws SQLException {
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
//        return materialBuyDAO.delete(paymentId);
    }
}
