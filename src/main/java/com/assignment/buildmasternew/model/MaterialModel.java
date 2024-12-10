package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.MaterialDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialModel {
    public String getNextMaterialId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Material_ID from Material order by Material_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MA%02d", newIdIndex);
        }
        return "MA01";
    }

    public ArrayList<String> getAllMaterialIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Material_ID from Material");
        ArrayList<String> materialIds = new ArrayList<>();

        while (rst.next()) {
            materialIds.add(rst.getString(1));
        }
        return materialIds;
    }

    public boolean saveMaterial(MaterialDto materialDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Material values (?,?,?,?)",
                materialDto.getMaterialId(),
                materialDto.getName(),
                materialDto.getQty(),
                materialDto.getUnit()
        );
    }

    public boolean updateMaterial(MaterialDto materialDto) throws SQLException {
        String sql = "update Material set Name=?, Quantity_in_Stock=?, Unit=? where Material_ID=?";
        return CrudUtil.execute(sql, materialDto.getName(), materialDto.getQty(), materialDto.getUnit(), materialDto.getMaterialId());
    }

    public boolean deleteMaterial(String materialId) throws SQLException {
        String sql = "DELETE FROM Material WHERE Material_ID=?";
        return CrudUtil.execute(sql, materialId);
    }

    public MaterialDto findById(String selectedMaterialId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Material where Material_ID=?", selectedMaterialId);
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

    public List<MaterialDto> getAllMaterials() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Material");
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

    public String getUnit(String materialId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Unit FROM Material where Material_ID=?", materialId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public String getName(String materialId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Name FROM Material where Material_ID=?", materialId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

}
