package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.MaterialDAO;
import com.assignment.buildmaster.dto.MaterialDto;
import com.assignment.buildmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Material_ID from Material order by Material_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MA%02d", newIdIndex);
        }
        return "MA01";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Material_ID from Material");
        ArrayList<String> materialIds = new ArrayList<>();

        while (rst.next()) {
            materialIds.add(rst.getString(1));
        }
        return materialIds;
    }

    public boolean save(MaterialDto materialDto) throws SQLException {
        return SQLUtil.execute(
                "insert into Material values (?,?,?,?)",
                materialDto.getMaterialId(),
                materialDto.getName(),
                materialDto.getQty(),
                materialDto.getUnit()
        );
    }

    public boolean update(MaterialDto materialDto) throws SQLException {
        String sql = "update Material set Name=?, Quantity_in_Stock=?, Unit=? where Material_ID=?";
        return SQLUtil.execute(sql, materialDto.getName(), materialDto.getQty(), materialDto.getUnit(), materialDto.getMaterialId());
    }

    public boolean delete(String materialId) throws SQLException {
        String sql = "DELETE FROM Material WHERE Material_ID=?";
        return SQLUtil.execute(sql, materialId);
    }

    public MaterialDto findById(String selectedMaterialId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Material where Material_ID=?", selectedMaterialId);
        if (rst.next()){
            return new MaterialDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public List<MaterialDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Material");
        List<MaterialDto> materialList = new ArrayList<>();

        while (rst.next()) {
            materialList.add(new MaterialDto(
                    rst.getString("Material_ID"),
                    rst.getString("Name"),
                    rst.getString("Quantity_in_Stock"),
                    rst.getString("Unit")
            ));
        }
        return materialList;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public String getUnit(String materialId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Unit FROM Material where Material_ID=?", materialId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<String> findAllIds() throws SQLException {
        return null;
    }

    @Override
    public String findNameById(String Id) throws SQLException {
        return "";
    }

    @Override
    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
        return null;
    }

    public String getName(String materialId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Material where Material_ID=?", materialId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public String getInfo(String Id) throws SQLException {
        return "";
    }

}
