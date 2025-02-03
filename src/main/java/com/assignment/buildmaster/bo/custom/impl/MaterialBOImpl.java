package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.MaterialBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.MaterialDAOImpl;
import com.assignment.buildmaster.dto.MaterialDto;
import com.assignment.buildmaster.entity.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBOImpl implements MaterialBO {
    MaterialDAOImpl materialDAO = (MaterialDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MATERIAL);
//    MaterialDAOImpl materialDAO = new MaterialDAOImpl();

    public String getNextMaterialId() throws SQLException {
        return materialDAO.getNextId();
    }

    public ArrayList<String> getAllMaterialIds() throws SQLException {
        return materialDAO.getAllIds();
    }

    public boolean saveMaterial(MaterialDto materialDto) throws SQLException {
        return materialDAO.save(new Material(materialDto.getMaterialId(), materialDto.getName(), materialDto.getQty(), materialDto.getUnit()));
    }

    public boolean updateMaterial(MaterialDto materialDto) throws SQLException {
        return materialDAO.update(new Material(materialDto.getMaterialId(), materialDto.getName(), materialDto.getQty(), materialDto.getUnit()));
    }

    public boolean deleteMaterial(String materialId) throws SQLException {
        return materialDAO.delete(materialId);
    }

    public MaterialDto findByMaterialId(String selectedMaterialId) throws SQLException {
        Material material = materialDAO.findById(selectedMaterialId);
        return new MaterialDto(material.getMaterialId(), material.getName(), material.getQty(), material.getUnit());
    }

    public List<MaterialDto> getAllMaterial() throws SQLException {
        List<Material> materials = materialDAO.getAll();
        List<MaterialDto> materialList = new ArrayList<>();
        for (Material material : materials) {
            materialList.add(new MaterialDto(material.getMaterialId(), material.getName(), material.getQty(), material.getUnit()));
        }
        return materialList;
    }

    public String getMaterialUnit(String materialId) throws SQLException {
        return materialDAO.getUnit(materialId);
    }

    public String getMaterialName(String materialId) throws SQLException {
        return materialDAO.getName(materialId);
    }
}
