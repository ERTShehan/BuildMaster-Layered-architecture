package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.EmployeeDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Employee_ID from Employee order by Employee_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public ArrayList<String> getAllEmployeeIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Employee_ID from Employee");
        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()){
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    public EmployeeDto findById(String selectedEmId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Employee where Employee_ID=?", selectedEmId);

        if (rst.next()) {
            return new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Employee values (?,?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getName(),
                employeeDto.getPhoneNo(),
                employeeDto.getAddress(),
                employeeDto.getRole()
        );
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        String sql = "update Employee set Name=?, Phone_No=?, Address=?, Role=? where Employee_ID=?";
        return CrudUtil.execute(sql, employeeDto.getName(), employeeDto.getPhoneNo(), employeeDto.getAddress(), employeeDto.getRole(), employeeDto.getEmployeeId());
    }

    public boolean deleteEmployee(String employeeID) throws SQLException {
        String sql = "DELETE FROM Employee WHERE Employee_ID=?";
        return CrudUtil.execute(sql, employeeID);
    }

    public List<EmployeeDto> getAllEmployees() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee order by Employee_ID asc");
        List<EmployeeDto> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new EmployeeDto(
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
        ResultSet rst = CrudUtil.execute("SELECT Name FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public String getRole(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Role FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
