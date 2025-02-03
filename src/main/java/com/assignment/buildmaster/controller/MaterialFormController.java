package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.MaterialBO;
import com.assignment.buildmaster.dto.MaterialDto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterialFormController implements Initializable {
    MaterialBO materialBO = (MaterialBO) BOFactory.getInstance().getBO(BOFactory.BOType.MATERIAL);
//    MaterialDAO materialDAO = new MaterialDAOImpl();

    @FXML
    private AnchorPane MaterialPane;

    @FXML
    private Button btnAllMaterial;

    @FXML
    private Button btnBuyMaterial;

    @FXML
    private Button btnConsumptionMaterial;

    @FXML
    private JFXButton btnMaterialDelete;

    @FXML
    private JFXButton btnMaterialReset;

    @FXML
    private JFXButton btnMaterialSave;

    @FXML
    private JFXButton btnMaterialUpdate;

    @FXML
    private JFXComboBox<String> cmbMaterialId;

    @FXML
    private Label lblMaterialID;

    @FXML
    private JFXTextField txtMaterialName;

    @FXML
    private JFXTextField txtMaterialQty;

    @FXML
    private JFXTextField txtUnit;

    @FXML
    void cmbProjectIdOnAction(ActionEvent event) throws SQLException {
        String selectedMaterialId = cmbMaterialId.getSelectionModel().getSelectedItem();
        if (selectedMaterialId != null) {
            MaterialDto materialDto = materialBO.findByMaterialId(selectedMaterialId);

            if (materialDto != null) {
                lblMaterialID.setText(materialDto.getMaterialId());
                txtMaterialName.setText(materialDto.getName());
                txtMaterialQty.setText(materialDto.getQty());
                txtUnit.setText(materialDto.getUnit());

                btnMaterialUpdate.setDisable(false);
                btnMaterialDelete.setDisable(false);
                btnMaterialSave.setDisable(true);
            }
        }
    }

    @FXML
    void onBuyMaterialAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MaterialBuy.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Material Buy Details");
            stage.setMinHeight(660);
            stage.setMinWidth(1360);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onConsumptionMaterialAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MaterialUsage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Material Consumption Table");
            stage.setMinHeight(660);
            stage.setMinWidth(1360);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onMaterialDeleteAction(ActionEvent event) {
        try {
            String materialId = lblMaterialID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this material?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();


            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = materialBO.deleteMaterial(materialId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete material.").show();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting material.").show();
        }
    }

    @FXML
    void onMaterialResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onMaterialSaveAction(ActionEvent event) throws SQLException {
        String materialId = lblMaterialID.getText();
        String materialName = txtMaterialName.getText();
        String materialQty = txtMaterialQty.getText();
        String materialUnit = txtUnit.getText();

        if(validation(materialName, materialQty, materialUnit)) {
            MaterialDto materialDto = new MaterialDto(materialId, materialName, materialQty, materialUnit);
            boolean isSaved = materialBO.saveMaterial(materialDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Material saved...!").show();
                refreshPage();
                resetFieldStyles();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Material...!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onMaterialUpdateAction(ActionEvent event) throws SQLException {
        String materialId = lblMaterialID.getText();
        String materialName = txtMaterialName.getText();
        String materialQty = txtMaterialQty.getText();
        String materialUnit = txtUnit.getText();

        if(validation(materialName, materialQty, materialUnit)) {
            try {
                MaterialDto materialDto = new MaterialDto(materialId, materialName, materialQty, materialUnit);
                boolean isUpdated = materialBO.updateMaterial(materialDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Material updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Material.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating Material.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onViewAllMaterial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllMaterialTable.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Material Table");
            stage.setMinHeight(500);
            stage.setMinWidth(650);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validation(String name, String materialQty, String materialUnit) {
        String validPattern = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{1,100}$";
        String qtyValidPattern = "^\\d+(\\.\\d{1,2})?$";

        boolean isValidName = name.matches(validPattern);
        boolean isValidQty = materialQty.matches(qtyValidPattern);
        boolean isValidUnit = materialUnit.matches(validPattern);

        txtMaterialName.setStyle(txtMaterialName.getStyle() + ";-fx-border-color: #7367F0;");
        txtMaterialQty.setStyle(txtMaterialQty.getStyle() + ";-fx-border-color: #7367F0;");
        txtUnit.setStyle(txtUnit.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtMaterialName.setStyle(txtMaterialName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidQty) {
            txtMaterialQty.setStyle(txtMaterialQty.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidUnit) {
            txtUnit.setStyle(txtUnit.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidQty && isValidUnit;
    }

    private void refreshPage() throws SQLException {
        String nextMaterialID = materialBO.getNextMaterialId();
        lblMaterialID.setText(nextMaterialID);

        loadMaterialIds();

        cmbMaterialId.getSelectionModel().clearSelection();
        txtMaterialName.clear();
        txtMaterialQty.clear();
        txtUnit.clear();

        lblMaterialID.setText(nextMaterialID);

        resetFieldStyles();

        btnMaterialSave.setDisable(false);
        btnMaterialUpdate.setDisable(true);
        btnMaterialDelete.setDisable(true);
    }

    private void loadMaterialIds() throws SQLException {
        ArrayList<String> materialIds = materialBO.getAllMaterialIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(materialIds);
        cmbMaterialId.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void resetFieldStyles() {
        txtMaterialName.setStyle("-fx-border-color: transparent;");
        txtMaterialQty.setStyle("-fx-border-color: transparent;");
        txtUnit.setStyle("-fx-border-color: transparent;");
    }
}