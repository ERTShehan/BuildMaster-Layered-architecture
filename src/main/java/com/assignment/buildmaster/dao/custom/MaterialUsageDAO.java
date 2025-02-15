package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.entity.MaterialUsage;

import java.sql.SQLException;
import java.util.List;

public interface MaterialUsageDAO extends CrudDAO<MaterialUsage> {
    public int getMaterialStock(String materialId) throws SQLException;
    public boolean updateMaterialStock(String materialId, int quantity) throws SQLException;

}
