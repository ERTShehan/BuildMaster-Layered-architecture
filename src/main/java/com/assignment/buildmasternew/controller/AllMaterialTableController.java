package com.assignment.buildmasternew.controller;

import com.assignment.buildmasternew.dto.MaterialDto;
import com.assignment.buildmasternew.dto.tm.MaterialTM;
import com.assignment.buildmasternew.model.MaterialModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class AllMaterialTableController {
    private final MaterialModel materialModel = new MaterialModel();

    @FXML
    private TableColumn<MaterialTM, String> colMaterialId;

    @FXML
    private TableColumn<MaterialTM, String> colMaterialName;

    @FXML
    private TableColumn<MaterialTM, String> colMaterialQty;

    @FXML
    private TableColumn<MaterialTM, String> colUnit;

    @FXML
    private AnchorPane materialTablePane;

    @FXML
    private TableView<MaterialDto> tblMaterials;

    public void initialize(){
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMaterialQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        loadMaterialData();
    }

    private void loadMaterialData(){
        try {
            List<MaterialDto> materials = materialModel.getAllMaterials();
            ObservableList<MaterialDto> materialList = FXCollections.observableArrayList(materials);
            tblMaterials.setItems(materialList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
