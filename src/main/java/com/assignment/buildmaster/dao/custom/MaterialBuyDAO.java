package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.MaterialBuy;

import java.sql.SQLException;

public interface MaterialBuyDAO extends CrudDAO<MaterialBuy> {
    public boolean updateMaterialStock(String materialId, int quantity) throws SQLException;
}
