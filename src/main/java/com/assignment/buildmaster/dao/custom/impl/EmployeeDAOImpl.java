package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.EmployeeDAO;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Employee_ID from Employee order by Employee_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Employee_ID from Employee");
        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()){
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    public Employee findById(String selectedEmId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Employee where Employee_ID=?", selectedEmId);

        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Employee values (?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getPhoneNo(),
                entity.getAddress(),
                entity.getRole()
        );
    }

    public boolean update(Employee entity) throws SQLException {
        String sql = "update Employee set Name=?, Phone_No=?, Address=?, Role=? where Employee_ID=?";
        return SQLUtil.execute(sql, entity.getName(), entity.getPhoneNo(), entity.getAddress(), entity.getRole(), entity.getEmployeeId());
    }

    public boolean delete(String employeeID) throws SQLException {
        String sql = "DELETE FROM Employee WHERE Employee_ID=?";
        return SQLUtil.execute(sql, employeeID);
    }

    public List<Employee> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Employee order by Employee_ID asc");
        List<Employee> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new Employee(
                    resultSet.getString("Employee_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone_No"),
                    resultSet.getString("Address"),
                    resultSet.getString("Role")
            ));
        }
        return paymentList;
    }

    public String getName(String employeeId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public String getInfo(String employeeId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Role FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

}
