package com.assignment.buildmaster.bo.custom;

import com.assignment.buildmaster.bo.SuperBO;
import com.assignment.buildmaster.dto.ProjectDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProjectBO extends SuperBO {
    String getNextProjectId() throws SQLException;
    ArrayList<String> getAllProjectIds() throws SQLException;
    ProjectDto findByProjectId(String projectId) throws SQLException;
    String findClientNameById(String clientId) throws SQLException;
    ArrayList<String> getAllProjectIdsByClient(String clientId) throws SQLException;
    ArrayList<String> findAllClientIds() throws SQLException;
    boolean saveProject(ProjectDto projectDto) throws SQLException;
    boolean updateProject(ProjectDto projectDto) throws SQLException;
    boolean deleteProject(String projectId) throws SQLException;
    List<ProjectDto> getAllProject() throws SQLException;
    String getProjectName(String projectId) throws SQLException;
}
