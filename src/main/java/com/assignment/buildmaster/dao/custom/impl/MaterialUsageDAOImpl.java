package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialUsageDAO;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.MaterialUsage;
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
    public MaterialUsage findById(String selectedId) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    public boolean save(MaterialUsage entity) throws SQLException {
        return SQLUtil.execute(
                "insert into MaterialUsage values (?,?,?,?,?)",
                entity.getUsageId(),
                entity.getProjectId(),
                entity.getMaterialId(),
                entity.getQuantityUsed(),
                entity.getDate()
        );
    }

    public boolean update(MaterialUsage entity) throws SQLException {
        return SQLUtil.execute(
                "update MaterialUsage set Project_ID=?, Material_ID=?, Quantity_used=?, Date=? where Usage_ID=?",
                entity.getProjectId(),
                entity.getMaterialId(),
                entity.getQuantityUsed(),
                entity.getDate(),
                entity.getUsageId()
        );
    }

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


    public boolean delete(String usageId) throws SQLException {
        String sql = "DELETE FROM MaterialUsage WHERE Usage_ID=?";
        return SQLUtil.execute(sql, usageId);
    }
}
