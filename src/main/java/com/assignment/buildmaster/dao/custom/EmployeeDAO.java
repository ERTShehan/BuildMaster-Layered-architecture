package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.Employee;

import java.sql.SQLException;


public interface EmployeeDAO extends CrudDAO<Employee> {
    String getName(String employeeId) throws SQLException;
    String getInfo(String employeeId) throws SQLException;
}
