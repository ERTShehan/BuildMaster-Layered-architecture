package com.assignment.buildmaster.controller;

import com.assignment.buildmaster.dao.custom.ProjectDAO;
import com.assignment.buildmaster.dto.ProjectDto;
import com.assignment.buildmaster.view.tdm.ProjectTM;
import com.assignment.buildmaster.dao.custom.Impl.ProjectDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AllProjectsTableController {

    @FXML
    private JFXButton btnCloseTable;

    @FXML
    private TableColumn<ProjectTM, String> colClientId;

    @FXML
    private TableColumn<ProjectTM, String> colProjectEnd;

    @FXML
    private TableColumn<ProjectTM, String> colProjectId;

    @FXML
    private TableColumn<ProjectTM, String> colProjectName;

    @FXML
    private TableColumn<ProjectTM, String> colProjectStart;

    @FXML
    private TableColumn<ProjectTM, String> colProjectStatus;

    @FXML
    private TableColumn<ProjectTM, String> colProjectType;

    @FXML
    private TableView<ProjectDto> tblProjects;

    ProjectDAO projectDAO = new ProjectDAOImpl();

    public void initialize(){
        colClientId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colProjectStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colProjectEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colProjectType.setCellValueFactory(new PropertyValueFactory<>("projectType"));
        colProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        loadProjectData();
    }

    private void loadProjectData() {
        try {
            List<ProjectDto> projects = projectDAO.getAll();
            ObservableList<ProjectDto> projectList = FXCollections.observableArrayList(projects);
            tblProjects.setItems(projectList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onProjectTableClose(ActionEvent event) {
        Stage stage = (Stage) btnCloseTable.getScene().getWindow();
        stage.close();
    }

}