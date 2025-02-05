package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialBuyBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.MaterialBuyDAOImpl;
import com.assignment.buildmaster.dto.MaterialBuyDto;

import java.sql.SQLException;
import java.util.List;

public class MaterialBuyBOImpl implements MaterialBuyBO {
    MaterialBuyDAOImpl materialBuyDAO = (MaterialBuyDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIALBUY);
//    MaterialBuyDAOImpl materialBuyDAO = new MaterialBuyDAOImpl();

    public String getNextMaterialBuyId() throws SQLException {
        return materialBuyDAO.getNextId();
    }

    public List<MaterialBuyDto> getAllMaterialBuy() throws SQLException {
        return materialBuyDAO.getAll();
    }

    public boolean saveMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        return materialBuyDAO.save(materialBuyDto);
    }

    public boolean updateMaterialBuy(MaterialBuyDto materialBuyDto) throws SQLException {
        return materialBuyDAO.update(materialBuyDto);
    }

    public boolean deleteMaterialBuy(String paymentId) throws SQLException {
        return materialBuyDAO.delete(paymentId);
    }
}
