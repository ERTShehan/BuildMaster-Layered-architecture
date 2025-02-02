package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProjectDAO extends CrudDAO<Project> {
    public String findNameById(String clientId) throws SQLException;
    public ArrayList<String> getAllIdsBy(String clientId) throws SQLException;
    public ArrayList<String> findAllIds() throws SQLException;
    public String getName(String projectId) throws SQLException;

//    public String getNextProjectId()/  getNextId() throws SQLException;
//    public ArrayList<String> getAllProjectIds() / getAllIds() throws SQLException;
//    public ProjectDto findByProjectId(String projectId) / findById(String projectId) throws SQLException;
//    public String findClientNameById(String clientId) / findNameById(String clientId) throws SQLException;
//    public ArrayList<String> getAllProjectIdsByClient(String clientId)/ getAllIdsBy(String clientId) throws SQLException;
//    public ArrayList<String> getAllClientIds() / findAllIds() throws SQLException;
//    public boolean saveProject(ProjectDto projectDto) throws SQLException;
//    public boolean updateProject(ProjectDto projectDto) throws SQLException;
//    public boolean deleteProject(String projectId) throws SQLException;
//    public List<ProjectDto> getAllProjects()/ getAll() throws SQLException;
//    public String getProjectNameById(String projectId)/ getInfo(String projectId) throws SQLException;
//    public String getName(String projectId) throws SQLException;
}
