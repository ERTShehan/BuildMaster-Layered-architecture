package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.MaterialBO;
import com.assignment.buildmaster.bo.custom.MaterialUsageBO;
import com.assignment.buildmaster.bo.custom.ProjectBO;
import com.assignment.buildmaster.dao.custom.MaterialDAO;
import com.assignment.buildmaster.dao.custom.MaterialUsageDAO;
import com.assignment.buildmaster.dao.custom.ProjectDAO;
import com.assignment.buildmaster.dto.MaterialUsageDto;
import com.assignment.buildmaster.view.tdm.MaterialUsageTM;
import com.assignment.buildmaster.dao.custom.impl.MaterialDAOImpl;
import com.assignment.buildmaster.dao.custom.impl.MaterialUsageDAOImpl;
import com.assignment.buildmaster.dao.custom.impl.ProjectDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterialUsageController implements Initializable {
    MaterialUsageBO materialUsageBO = (MaterialUsageBO) BOFactory.getInstance().getBO(BOFactory.BOType.MATERIALUSAGE);
    ProjectBO projectBO = (ProjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROJECT);
    MaterialBO materialBO = (MaterialBO) BOFactory.getInstance().getBO(BOFactory.BOType.MATERIAL);

//    MaterialUsageDAO materialUsageDAO = new MaterialUsageDAOImpl();
//    ProjectDAO projectDAO = new ProjectDAOImpl();
//    MaterialDAO materialDAO = new MaterialDAOImpl();

    @FXML
    private JFXButton btnBackUsage;

    @FXML
    private JFXButton btnUsageDelete;

    @FXML
    private JFXButton btnUsageReset;

    @FXML
    private JFXButton btnUsageSave;

    @FXML
    private JFXButton btnUsageUpdate;

    @FXML
    private JFXComboBox<String> cmbMaterialIdUsage;

    @FXML
    private JFXComboBox<String> cmbProjectIdUsage;

    @FXML
    private TableColumn<MaterialUsageTM, String> colQuantityUsed;

    @FXML
    private TableColumn<MaterialUsageTM, String> colUsageDate;

    @FXML
    private TableColumn<MaterialUsageTM, String> colUsageId;

    @FXML
    private TableColumn<MaterialUsageTM, String> colUsageMaterialId;

    @FXML
    private TableColumn<MaterialUsageTM, String> colUsageProjectId;

    @FXML
    private Label lblMaterialNameUsage;

    @FXML
    private Label lblProjectNameUsage;

    @FXML
    private Label lblUsageDate;

    @FXML
    private Label lblUsageID;

    @FXML
    private Label lblUsageUnit;

    @FXML
    private AnchorPane materialUsagePane;

    @FXML
    private TableView<MaterialUsageTM> tblMaterialUsage;

    @FXML
    private JFXTextField txtQuantityUsed;

    @FXML
    void onClickTable(MouseEvent event) {
        MaterialUsageTM selectedItem = tblMaterialUsage.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblUsageID.setText(selectedItem.getUsageId());
            cmbProjectIdUsage.setValue(selectedItem.getProjectId());
            cmbMaterialIdUsage.setValue(selectedItem.getMaterialId());
            txtQuantityUsed.setText(selectedItem.getQuantityUsed());
            lblUsageDate.setText(selectedItem.getDate());

            btnUsageSave.setDisable(true);
            btnUsageDelete.setDisable(false);
            btnUsageUpdate.setDisable(true);
        }
    }

    @FXML
    void cmbMaterialIdLoadUsage(ActionEvent event) throws SQLException {
        String selectedMaterialId = cmbMaterialIdUsage.getSelectionModel().getSelectedItem();
        if (selectedMaterialId != null) {
            lblUsageUnit.setText(materialBO.getMaterialUnit(selectedMaterialId));
            lblMaterialNameUsage.setText(materialBO.getMaterialName(selectedMaterialId));
        }
//        btnUsageSave.setDisable(false);

        checkComboBoxSelection();
    }

    @FXML
    void cmbProjectIdLoadUsage(ActionEvent event) throws SQLException {
        String selectedProjectId = cmbProjectIdUsage.getSelectionModel().getSelectedItem();
        if (selectedProjectId != null) {
            lblProjectNameUsage.setText(projectBO.getProjectName(selectedProjectId));
        }
//        btnUsageSave.setDisable(false);

        checkComboBoxSelection();
    }

    private void checkComboBoxSelection() {
        boolean isBothSelected = cmbProjectIdUsage.getSelectionModel().getSelectedItem() != null && cmbMaterialIdUsage.getSelectionModel().getSelectedItem() != null;
        btnUsageSave.setDisable(!isBothSelected);
    }


    @FXML
    void onBackUsageAction(ActionEvent event) {
        Stage stage = (Stage) btnBackUsage.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onUsageDeleteAction(ActionEvent event) {
        try {
            String usageId = lblUsageID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Material Usage?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = materialUsageBO.deleteMaterialUsage(usageId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Usage deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Material Usage.").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Material Usage.").show();
        }
    }

    @FXML
    void onUsageResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onUsageSaveAction(ActionEvent event) {
        String usageId = lblUsageID.getText();
        String projectId = cmbProjectIdUsage.getSelectionModel().getSelectedItem();
        String materialId = cmbMaterialIdUsage.getSelectionModel().getSelectedItem();
        String quantityUsed = txtQuantityUsed.getText();
        String date = lblUsageDate.getText();

        if(validation(quantityUsed)) {
            try {
                MaterialUsageDto materialUsageDto = new MaterialUsageDto(usageId, projectId, materialId, quantityUsed, date);
                boolean isSaved = materialUsageBO.saveMaterialUsage(materialUsageDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Usage saved...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
//                    new Alert(Alert.AlertType.ERROR, "Fail to save Material Usage...!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating Material Usage.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    private boolean validation(String quantityUsed){
        String qtyValidPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidQuantity =  quantityUsed.matches(qtyValidPattern);
        txtQuantityUsed.setStyle(txtQuantityUsed.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidQuantity) {
            txtQuantityUsed.setStyle(txtQuantityUsed.getStyle() + ";-fx-border-color: red;");
        }

        return isValidQuantity;
    }

    @FXML
    void onUsageUpdateAction(ActionEvent event) {
        String projectId = cmbProjectIdUsage.getSelectionModel().getSelectedItem();
        String materialId = cmbMaterialIdUsage.getSelectionModel().getSelectedItem();
        String usageId = lblUsageID.getText();
        String date = lblUsageDate.getText();
        String quantityUsed = txtQuantityUsed.getText();

        if(validation(quantityUsed)) {
            try {
                MaterialUsageDto materialUsageDto = new MaterialUsageDto(usageId, projectId, materialId, quantityUsed, date);
                boolean isUpdated = materialUsageBO.updateMaterialUsage(materialUsageDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Usage saved...!").show();
                    refreshPage();
                    resetFieldStyles();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save Material Usage...!").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUsageId.setCellValueFactory(new PropertyValueFactory<>("usageId"));
        colUsageProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colUsageMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colQuantityUsed.setCellValueFactory(new PropertyValueFactory<>("quantityUsed"));
        colUsageDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        lblUsageID.setText(materialUsageBO.getNextMaterialUsageId());
        loadProjectIds();
        loadMaterialIds();
        lblUsageDate.setText(LocalDate.now().toString());
        cmbProjectIdUsage.getSelectionModel().clearSelection();
        cmbMaterialIdUsage.getSelectionModel().clearSelection();
        txtQuantityUsed.clear();
        lblProjectNameUsage.setText("");
        lblMaterialNameUsage.setText("");
        lblUsageID.setText("");
        lblUsageUnit.setText("");
        resetFieldStyles();
        lblUsageID.setText(materialUsageBO.getNextMaterialUsageId());

        btnUsageSave.setDisable(true);
        btnUsageUpdate.setDisable(true);
        btnUsageDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<MaterialUsageDto> usageDTOS = (ArrayList<MaterialUsageDto>) materialUsageBO.getAllMaterialUsage();
        ObservableList<MaterialUsageTM> usageTMS = FXCollections.observableArrayList();
        for (MaterialUsageDto materialUsageDto : usageDTOS){
            MaterialUsageTM materialUsageTM = new MaterialUsageTM(
                    materialUsageDto.getUsageId(),
                    materialUsageDto.getProjectId(),
                    materialUsageDto.getMaterialId(),
                    materialUsageDto.getQuantityUsed(),
                    materialUsageDto.getDate()
            );
            usageTMS.add(materialUsageTM);
        }

        tblMaterialUsage.setItems(usageTMS);
    }

    private void loadProjectIds() throws SQLException {
        ArrayList<String> projectIds = projectBO.getAllProjectIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(projectIds);
        cmbProjectIdUsage.setItems(observableList);
    }

    private void loadMaterialIds() throws SQLException {
        ArrayList<String> materialIds = materialBO.getAllMaterialIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(materialIds);
        cmbMaterialIdUsage.setItems(observableList);
    }

    private void resetFieldStyles(){
        txtQuantityUsed.setStyle("-fx-border-color: transparent");
    }
}
