package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.MachineDAO;
import com.assignment.buildmaster.dto.MachineDto;
import com.assignment.buildmaster.dao.custom.Impl.MachineDAOImpl;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MachineFormController implements Initializable {
    MachineDAO machineDAO = new MachineDAOImpl();

    @FXML
    private JFXButton btnMachineDelete;

    @FXML
    private JFXButton btnMachineReset;

    @FXML
    private JFXButton btnMachineSave;

    @FXML
    private JFXButton btnMachineUpdate;

    @FXML
    private JFXComboBox<String> cmbMachineId;

    @FXML
    private Label lblMachineID;

    @FXML
    private JFXTextField txtMachineDescription;

    @FXML
    private JFXTextField txtMachineName;

    @FXML
    private JFXTextField txtMachineStatus;

    @FXML
    void cmbMachineOnAction(ActionEvent event) throws SQLException {
        String selectedMachineId = cmbMachineId.getSelectionModel().getSelectedItem();
        if (selectedMachineId != null) {
            MachineDto machineDto = machineDAO.findById(selectedMachineId);

            if (machineDto != null) {
                lblMachineID.setText(machineDto.getMachineId());
                txtMachineName.setText(machineDto.getName());
                txtMachineStatus.setText(machineDto.getStatus());
                txtMachineDescription.setText(machineDto.getDescription());

                btnMachineUpdate.setDisable(false);
                btnMachineDelete.setDisable(false);

                btnMachineSave.setDisable(true);
            }
        }
    }

    @FXML
    void onDeleteMachine(ActionEvent event) {
        try {
            String machineID = lblMachineID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this machine?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();


            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = machineDAO.delete(machineID);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete machine.").show();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting client.").show();
        }
    }

    @FXML
    void onLoadMachineTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllMachineTable.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMachineResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onSaveMachine(ActionEvent event) throws SQLException {
        String machineId = lblMachineID.getText();
        String machineName = txtMachineName.getText();
        String machineStatus = txtMachineStatus.getText();
        String machineDescription = txtMachineDescription.getText();

        if (validation(machineName, machineStatus, machineDescription)) {
            MachineDto machineDto = new MachineDto(machineId, machineName, machineStatus, machineDescription);

            boolean isSaved = machineDAO.save(machineDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Machine saved...!").show();
                refreshPage();
                resetFieldStyles();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Machine...!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onUpdateMachine(ActionEvent event) throws SQLException {
        String machineId = lblMachineID.getText();
        String machineName = txtMachineName.getText();
        String machineStatus = txtMachineStatus.getText();
        String machineDescription = txtMachineDescription.getText();

        if (validation(machineName, machineStatus, machineDescription)) {
            try {
                MachineDto machineDto = new MachineDto(machineId, machineName, machineStatus, machineDescription);
                boolean isUpdated = machineDAO.update(machineDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Machine.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating Machine.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    private boolean validation(String machineName, String machineStatus, String machineDescription) {
        String validPattern = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{1,150}$";

        boolean isValidName = machineName.matches(validPattern);
        boolean isValidStatus = machineStatus.matches(validPattern);
        boolean isValidDescription = machineDescription.matches(validPattern);

        txtMachineName.setStyle(txtMachineName.getStyle() + ";-fx-border-color: #7367F0;");
        txtMachineStatus.setStyle(txtMachineStatus.getStyle() + ";-fx-border-color: #7367F0;");
        txtMachineDescription.setStyle(txtMachineDescription.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtMachineName.setStyle(txtMachineName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidStatus) {
            txtMachineStatus.setStyle(txtMachineStatus.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidDescription) {
            txtMachineDescription.setStyle(txtMachineDescription.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidStatus && isValidDescription;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        String nextMachineID = machineDAO.getNextId();
        lblMachineID.setText(nextMachineID);

        loadMachineIds();

        cmbMachineId.getSelectionModel().clearSelection();
        txtMachineName.clear();
        txtMachineStatus.clear();
        txtMachineDescription.clear();

        lblMachineID.setText(nextMachineID);

        resetFieldStyles();

        btnMachineSave.setDisable(false);
        btnMachineUpdate.setDisable(true);
        btnMachineDelete.setDisable(true);
    }

    private void loadMachineIds() throws SQLException {
        ArrayList<String> machineIds = machineDAO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(machineIds);
        cmbMachineId.setItems(observableList);
    }

    private void resetFieldStyles() {
        txtMachineName.setStyle("-fx-border-color: transparent;");
        txtMachineStatus.setStyle("-fx-border-color: transparent;");
        txtMachineDescription.setStyle("-fx-border-color: transparent;");
    }

}
