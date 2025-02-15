package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDAO extends CrudDAO<Material> {
    String getUnit(String materialId) throws SQLException;
    String getName(String materialId) throws SQLException;
//    public String getNextMaterialId() throws SQLException;
//    public ArrayList<String> getAllMaterialIds() throws SQLException;
//    public boolean saveMaterial(MaterialDto materialDto) throws SQLException;
//    public boolean updateMaterial(MaterialDto materialDto) throws SQLException;
//    public boolean deleteMaterial(String materialId) throws SQLException;
//    public MaterialDto findById(String selectedMaterialId) throws SQLException;
//    public List<MaterialDto> getAllMaterials() throws SQLException;
//    public String getUnit(String materialId) throws SQLException;
//    public String getName(String materialId) throws SQLException;
}
