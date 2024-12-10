package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.tm.ProjectExpensesTM;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardModel {
    public ArrayList<String> getAllProjectName() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Name from Project");
        ArrayList<String> projectNames = new ArrayList<>();

        while (rst.next()) {
            projectNames.add(rst.getString(1));
        }
        return projectNames;
    }

    public double getTotalIncome() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(Amount) AS Total_Amount FROM Payment");
        if (rst.next()) {
            return rst.getInt("Total_Amount");
        }
        return 0;
    }

    public double getAllExpenses() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(Amount) AS Total_Expenses FROM Expenses");
        if (rst.next()) {
            return rst.getInt("Total_Expenses");
        }
        return 0;
    }

    public ArrayList<ProjectExpensesTM> getProjectDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT " +
                        "P.Project_ID, " +
                        "P.Name AS Project_Name, " +
                        "COALESCE(SUM(E.Amount), 0) AS Total_Expenses, " +
                        "COALESCE(SUM(MU.Quantity_used * MB.Unit_Amount), 0) AS Total_Material_Cost, " +
                        "COALESCE(SUM(E.Amount), 0) + COALESCE(SUM(MU.Quantity_used * MB.Unit_Amount), 0) AS Project_Total_Cost " +
                        "FROM Project P " +
                        "LEFT JOIN Expenses E ON P.Project_ID = E.Project_ID " +
                        "LEFT JOIN MaterialUsage MU ON P.Project_ID = MU.Project_ID " +
                        "LEFT JOIN MaterialBuy MB ON MU.Material_ID = MB.Material_ID " +
                        "GROUP BY P.Project_ID, P.Name " +
                        "ORDER BY Project_Total_Cost DESC");

        ArrayList<ProjectExpensesTM> projectList = new ArrayList<>();

        while (rst.next()) {
            String projectID = rst.getString("Project_ID");
            String projectName = rst.getString("Project_Name");
            double totalExpenses = rst.getDouble("Total_Expenses");
            double totalMaterialCost = rst.getDouble("Total_Material_Cost");
            double projectTotalCost = rst.getDouble("Project_Total_Cost");

            projectList.add(new ProjectExpensesTM(projectID, projectName, totalExpenses, totalMaterialCost, projectTotalCost));
        }
        return projectList;
    }
}
