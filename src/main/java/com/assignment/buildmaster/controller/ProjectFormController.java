package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.bo.BOFactory;
import com.assignment.buildmaster.bo.custom.ProjectBO;
import com.assignment.buildmaster.dao.custom.ProjectDAO;
import com.assignment.buildmaster.dto.ProjectDto;
import com.assignment.buildmaster.dao.custom.impl.ProjectDAOImpl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectFormController implements Initializable {

    ProjectBO projectBO = (ProjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROJECT);
//    ProjectDAO projectDAO = new ProjectDAOImpl();

    @FXML
    private Label lblProjectID;

    @FXML
    private Button btnAllProject;

    @FXML
    private JFXButton btnProjectDelete;

    @FXML
    private JFXButton btnProjectReset;

    @FXML
    private JFXButton btnProjectSave;

    @FXML
    private JFXButton btnProjectUpdate;

    @FXML
    private JFXComboBox<String> cmbClientId;

    @FXML
    private JFXComboBox<String> cmbProjectId;

    @FXML
    private Label lblProjectClientName;

    @FXML
    private JFXTextField txtProjectEnd;

    @FXML
    private JFXTextField txtProjectName;

    @FXML
    private JFXTextField txtProjectStart;

    @FXML
    private JFXTextField txtProjectStatus;

    @FXML
    private JFXTextField txtProjectType;

    @FXML
    private void cmbClientIdLoadProject(ActionEvent event) throws SQLException {
        String selectedClientId = cmbClientId.getSelectionModel().getSelectedItem();
        if (selectedClientId != null) {
            lblProjectClientName.setText(projectBO.findClientNameById(selectedClientId));
            loadProjectIdsByClient(selectedClientId);
        }

        btnProjectSave.setDisable(false);
    }

    @FXML
    private void cmbProjectIdOnAction(ActionEvent event) throws SQLException {
        String selectedProjectId = cmbProjectId.getSelectionModel().getSelectedItem();
        if (selectedProjectId != null) {
            ProjectDto projectDto = projectBO.findByProjectId(selectedProjectId);
            if (projectDto != null) {
                lblProjectClientName.setText(projectBO.findClientNameById(projectDto.getClientId()));

                lblProjectID.setText(projectDto.getProjectId());
                txtProjectName.setText(projectDto.getProjectName());
                txtProjectStart.setText(projectDto.getStartDate());
                txtProjectEnd.setText(projectDto.getEndDate());
                txtProjectType.setText(projectDto.getProjectType());
                txtProjectStatus.setText(projectDto.getStatus());
                cmbClientId.setValue(projectDto.getClientId());

                btnProjectUpdate.setDisable(false);
                btnProjectDelete.setDisable(false);

                btnProjectSave.setDisable(true);
            }
        }
    }

    @FXML
    void onProjectDeleteAction(ActionEvent event) {
        try {
            String projectId = lblProjectID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this project?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if(buttonType.get() == ButtonType.YES){
                boolean isDeleted = projectBO.deleteProject(projectId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Client deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete client.").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting client.").show();
        }
    }

    @FXML
    void onProjectResetAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onProjectSaveAction(ActionEvent event) throws SQLException {
        String projectId = lblProjectID.getText();
        String projectName = txtProjectName.getText();
        String startDate = txtProjectStart.getText();
        String endDate = txtProjectEnd.getText();
        String projectType = txtProjectType.getText();
        String status = txtProjectStatus.getText();
        String clientId = cmbClientId.getSelectionModel().getSelectedItem();

        String wordPattern = "^[a-zA-Z0-9\\s]{3,50}$";
        String datePattern = "^\\d{1,4}-\\d{1,2}-\\d{1,4}$";

        boolean isValidName = projectName.matches(wordPattern);
        boolean isValidStartDate = startDate.matches(datePattern);
        boolean isValidEndDate = endDate.matches(datePattern);
        boolean isValidType = projectType.matches(wordPattern);
        boolean isValidStatus = status.matches(wordPattern);

        txtProjectName.setStyle(txtProjectName.getStyle() + ";-fx-border-color: #7367F0;");
        txtProjectStart.setStyle(txtProjectStart.getStyle() + ";-fx-border-color: #7367F0;");
        txtProjectEnd.setStyle(txtProjectEnd.getStyle() + ";-fx-border-color: #7367F0;");
        txtProjectType.setStyle(txtProjectType.getStyle() + ";-fx-border-color: #7367F0;");
        txtProjectStatus.setStyle(txtProjectStatus.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtProjectName.setStyle(txtProjectName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidStartDate) {
            txtProjectStart.setStyle(txtProjectStart.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEndDate) {
            txtProjectEnd.setStyle(txtProjectEnd.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidType) {
            txtProjectType.setStyle(txtProjectType.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidStatus) {
            txtProjectStatus.setStyle(txtProjectStatus.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidStartDate && isValidEndDate && isValidType && isValidStatus){
            ProjectDto projectDto = new ProjectDto(projectId, projectName, startDate, endDate, projectType, status, clientId);

            boolean isSaved = projectBO.saveProject(projectDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Project saved...!").show();
                refreshPage();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Client...!").show();
            }
        }

    }

    @FXML
    void onProjectUpdateAction(ActionEvent event) {
        String projectId = lblProjectID.getText();
        String projectName = txtProjectName.getText();
        String startDate = txtProjectStart.getText();
        String endDate = txtProjectEnd.getText();
        String projectType = txtProjectType.getText();
        String status = txtProjectStatus.getText();
        String clientId = cmbClientId.getSelectionModel().getSelectedItem();

        String wordPattern = "^[a-zA-Z0-9\\s]{3,50}$";
        String datePattern = "^\\d{1,4}-\\d{1,2}-\\d{1,4}$";

        boolean isValidName = projectName.matches(wordPattern);
        boolean isValidStartDate = startDate.matches(datePattern);
        boolean isValidEndDate = endDate.matches(datePattern);
        boolean isValidType = projectType.matches(wordPattern);
        boolean isValidStatus = status.matches(wordPattern);

        resetFieldStyles();

        if (!isValidName) {
            txtProjectName.setStyle("-fx-border-color: red;");
        }
        if (!isValidStartDate) {
            txtProjectStart.setStyle("-fx-border-color: red;");
        }
        if (!isValidEndDate) {
            txtProjectEnd.setStyle("-fx-border-color: red;");
        }
        if (!isValidType) {
            txtProjectType.setStyle("-fx-border-color: red;");
        }

        if (!isValidStatus) {
            txtProjectStatus.setStyle("-fx-border-color: red;");
        }

        if (isValidName && isValidStartDate && isValidEndDate && isValidType && isValidStatus) {
            try {
                ProjectDto projectDto = new ProjectDto(projectId, projectName, startDate, endDate, projectType, status, clientId);
                boolean isUpdated = projectBO.updateProject(projectDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Project updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update project.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating project.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted fields.").show();
        }
    }

    @FXML
    void onViewAllProject(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllProjectsTable.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        lblProjectID.setText(projectBO.getNextProjectId());
        loadClientIds();
        loadProjectIds();
        cmbClientId.getSelectionModel().clearSelection();
        cmbProjectId.getSelectionModel().clearSelection();
        txtProjectName.clear();
        txtProjectType.clear();
        txtProjectStart.clear();
        txtProjectEnd.clear();
        txtProjectStatus.clear();
        lblProjectClientName.setText("");
        lblProjectID.setText(projectBO.getNextProjectId());
        resetFieldStyles();

        btnProjectSave.setDisable(true);
        btnProjectUpdate.setDisable(true);
        btnProjectDelete.setDisable(true);
    }

    private void resetFieldStyles() {
        txtProjectName.setStyle("-fx-border-color: transparent;");
        txtProjectStart.setStyle("-fx-border-color: transparent;");
        txtProjectEnd.setStyle("-fx-border-color: transparent;");
        txtProjectType.setStyle("-fx-border-color: transparent;");
        txtProjectStatus.setStyle("-fx-border-color: transparent;");
    }

    private void loadClientIds() throws SQLException {
        ArrayList<String> clientIds = projectBO.findAllClientIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(clientIds);
        cmbClientId.setItems(observableList);
    }

    private void loadProjectIds() throws SQLException {
        ArrayList<String> ProjectIds = projectBO.getAllProjectIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(ProjectIds);
        cmbProjectId.setItems(observableList);
    }

    private void loadProjectIdsByClient(String clientId) throws SQLException {
        ArrayList<String> projectIds = projectBO.getAllProjectIdsByClient(clientId);
        cmbProjectId.setItems(FXCollections.observableArrayList(projectIds));
    }
}