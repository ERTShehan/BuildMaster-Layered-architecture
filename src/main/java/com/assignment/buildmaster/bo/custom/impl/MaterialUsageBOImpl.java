package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialUsageBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.MaterialUsageDAOImpl;
import com.assignment.buildmaster.dto.MaterialUsageDto;

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
        return materialUsageDAO.findById(selectedId);
    }

    public ArrayList<String> getAllMaterialUsageIds() throws SQLException {
        return materialUsageDAO.getAllIds();
    }

    public boolean saveMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        return materialUsageDAO.save(materialUsageDto);
    }

    public boolean updateMaterialUsage(MaterialUsageDto materialUsageDto) throws SQLException {
        return materialUsageDAO.update(materialUsageDto);
    }

    public List<MaterialUsageDto> getAllMaterialUsage() throws SQLException {
        return materialUsageDAO.getAll();
    }

    public boolean deleteMaterialUsage(String usageId) throws SQLException {
        return materialUsageDAO.delete(usageId);
    }
}
