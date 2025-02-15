package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialUsageDAO;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.MaterialUsage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialUsageDAOImpl implements MaterialUsageDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Usage_ID FROM MaterialUsage ORDER BY Usage_ID DESC LIMIT 1");
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
    public MaterialUsage findById(String selectedId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialUsage WHERE Usage_ID = ?", selectedId);
        if (rst.next()) {
            return new MaterialUsage(
                    rst.getString("Usage_ID"),
                    rst.getString("Project_ID"),
                    rst.getString("Material_ID"),
                    rst.getString("Quantity_used"),
                    rst.getString("Date")
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Usage_ID FROM MaterialUsage");
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }

    @Override
    public boolean save(MaterialUsage entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO MaterialUsage VALUES (?,?,?,?,?)",
                entity.getUsageId(),
                entity.getProjectId(),
                entity.getMaterialId(),
                entity.getQuantityUsed(),
                entity.getDate()
        );
    }

    @Override
    public boolean update(MaterialUsage entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE MaterialUsage SET Project_ID=?, Material_ID=?, Quantity_used=?, Date=? WHERE Usage_ID=?",
                entity.getProjectId(),
                entity.getMaterialId(),
                entity.getQuantityUsed(),
                entity.getDate(),
                entity.getUsageId()
        );
    }

    @Override
    public List<MaterialUsage> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM MaterialUsage");
        List<MaterialUsage> usageList = new ArrayList<>();
        while (rst.next()) {
            usageList.add(new MaterialUsage(
                    rst.getString("Usage_ID"),
                    rst.getString("Project_ID"),
                    rst.getString("Material_ID"),
                    rst.getString("Quantity_used"),
                    rst.getString("Date")
            ));
        }
        return usageList;
    }

    @Override
    public boolean delete(String usageId) throws SQLException {
        return SQLUtil.execute("DELETE FROM MaterialUsage WHERE Usage_ID=?", usageId);
    }

    @Override
    public int getMaterialStock(String materialId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Quantity_in_Stock FROM Material WHERE Material_ID = ?", materialId);
        if (rst.next()) {
            return rst.getInt("Quantity_in_Stock");
        }
        return 0;
    }

    @Override
    public boolean updateMaterialStock(String materialId, int quantity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Material SET Quantity_in_Stock = Quantity_in_Stock + ? WHERE Material_ID = ?",
                quantity,
                materialId
        );
    }
}