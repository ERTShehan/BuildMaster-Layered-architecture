package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.EmployeeBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.EmployeeDAOImpl;
import com.assignment.buildmaster.dto.EmployeeDto;
import com.assignment.buildmaster.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAOImpl employeeDAO = (EmployeeDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    public String getNextEmployeeId() throws SQLException {
        return employeeDAO.getNextId();
    }

    public ArrayList<String> getAllEmployeeIds() throws SQLException {
        return employeeDAO.getAllIds();
    }

    public EmployeeDto findByEmployeeId(String selectedEmId) throws SQLException {
        Employee employee = employeeDAO.findById(selectedEmId);
        return new EmployeeDto(employee.getEmployeeId(), employee.getName(), employee.getPhoneNo(), employee.getAddress(), employee.getRole());
    }

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        return employeeDAO.save(new Employee(employeeDto.getEmployeeId(), employeeDto.getName(), employeeDto.getPhoneNo(), employeeDto.getAddress(), employeeDto.getRole()));
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        return employeeDAO.update(new Employee(employeeDto.getEmployeeId(), employeeDto.getName(), employeeDto.getPhoneNo(), employeeDto.getAddress(), employeeDto.getRole()));
    }

    public boolean deleteEmployee(String employeeID) throws SQLException {
        return employeeDAO.delete(employeeID);
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeDto> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeList.add(new EmployeeDto(employee.getEmployeeId(), employee.getName(), employee.getPhoneNo(), employee.getAddress(), employee.getRole()));
        }
        return employeeList;
    }

    public String getEmployeeName(String employeeId) throws SQLException {
        return employeeDAO.getName(employeeId);
    }

    public String getEmployeeRole(String employeeId) throws SQLException {
        return employeeDAO.getInfo(employeeId);
    }


//    public int getCount() throws SQLException {
//
//    }

//    public String getUnit(String Id) throws SQLException {
//        return "";
//    }
//
//    public ArrayList<String> findAllIds() throws SQLException {
//        return null;
//    }
//
//    public String findNameById(String Id) throws SQLException {
//        return "";
//    }
//
//    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
//        return null;
//    }
}
