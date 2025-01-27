package com.assignment.buildmaster.dao.custom.Impl;

import com.assignment.buildmaster.dto.ProjectDto;
import com.assignment.buildmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl {
    public String getNextProjectId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Project_ID from Project order by Project_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public ArrayList<String> getAllProjectIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Project_ID from Project");
        ArrayList<String> projectIds = new ArrayList<>();

        while (rst.next()){
            projectIds.add(rst.getString(1));
        }

        return projectIds;
    }

    public ProjectDto findByProjectId(String projectId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Project WHERE Project_ID=?", projectId);
        if (rst.next()) {
            return new ProjectDto(
                    rst.getString(1),  //project id
                    rst.getString(2), //Name
                    rst.getString(3),  //Start date
                    rst.getString(4), //End date
                    rst.getString(5), //Type
                    rst.getString(6),  //Status
                    rst.getString(7)  //Client id
            );
        }
        return null;
    }

    public String findClientNameById(String clientId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Client WHERE Client_ID=?", clientId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<String> getAllProjectIdsByClient(String clientId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Project_ID FROM Project WHERE Client_ID=?", clientId);
        ArrayList<String> projectIds = new ArrayList<>();
        while (rst.next()) {
            projectIds.add(rst.getString(1));
        }
        return projectIds;
    }

    public ArrayList<String> getAllClientIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Client_ID from Client");
        ArrayList<String> clientIds = new ArrayList<>();

        while (rst.next()){
            clientIds.add(rst.getString(1));
        }

        return clientIds;
    }

    public boolean saveProject(ProjectDto projectDto) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO Project (Project_ID, Name, Start_date, End_date, Type, Status, Client_ID) VALUES (?, ?, ?, ?, ?, ?, ?)",
                projectDto.getProjectId(),
                projectDto.getProjectName(),
                projectDto.getStartDate(),
                projectDto.getEndDate(),
                projectDto.getProjectType(),
                projectDto.getStatus(),
                projectDto.getClientId()
        );
    }

    public boolean updateProject(ProjectDto projectDto) throws SQLException {
        return SQLUtil.execute(
                "update Project set name=?, Start_date=?, End_date=?, Type=?, Status=? where Project_ID=?",
                projectDto.getProjectName(),
                projectDto.getStartDate(),
                projectDto.getEndDate(),
                projectDto.getProjectType(),
                projectDto.getStatus(),
                projectDto.getProjectId()
//                projectDto.getClientId()
        );
    }

    public boolean deleteProject(String projectId) throws SQLException {
        String sql = "DELETE FROM Project WHERE Project_ID=?";
        return SQLUtil.execute(sql, projectId);
    }

    public List<ProjectDto> getAllProjects() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Project order by Project_ID asc");
        List<ProjectDto> projectList = new ArrayList<>();

        while (resultSet.next()) {
            projectList.add(new ProjectDto(
                    resultSet.getString("Project_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Start_date"),
                    resultSet.getString("End_date"),
                    resultSet.getString("Type"),
                    resultSet.getString("Status"),
                    resultSet.getString("Client_ID")
            ));
        }
        return projectList;
    }

    public String getProjectNameById(String projectId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Project where Project_ID=?", projectId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public String getName(String projectId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Project where Project_ID=?", projectId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
