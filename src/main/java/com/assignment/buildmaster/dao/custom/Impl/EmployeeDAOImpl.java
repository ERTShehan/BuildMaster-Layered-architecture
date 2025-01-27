package com.assignment.buildmaster.dao.custom.Impl;

import com.assignment.buildmaster.dao.custom.EmployeeDAO;
import com.assignment.buildmaster.dto.EmployeeDto;
import com.assignment.buildmaster.dao.SQLUtil;

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

    public EmployeeDto findById(String selectedEmId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Employee where Employee_ID=?", selectedEmId);

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

    public boolean save(EmployeeDto employeeDto) throws SQLException {
        return SQLUtil.execute(
                "insert into Employee values (?,?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getName(),
                employeeDto.getPhoneNo(),
                employeeDto.getAddress(),
                employeeDto.getRole()
        );
    }

    public boolean update(EmployeeDto employeeDto) throws SQLException {
        String sql = "update Employee set Name=?, Phone_No=?, Address=?, Role=? where Employee_ID=?";
        return SQLUtil.execute(sql, employeeDto.getName(), employeeDto.getPhoneNo(), employeeDto.getAddress(), employeeDto.getRole(), employeeDto.getEmployeeId());
    }

    public boolean delete(String employeeID) throws SQLException {
        String sql = "DELETE FROM Employee WHERE Employee_ID=?";
        return SQLUtil.execute(sql, employeeID);
    }

    public List<EmployeeDto> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Employee order by Employee_ID asc");
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

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public String getName(String employeeId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public String getRole(String employeeId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Role FROM Employee where Employee_ID=?", employeeId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
