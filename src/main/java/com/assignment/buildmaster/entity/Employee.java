package com.assignment.buildmaster.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private String employeeId;
    private String name;
    private String phoneNo;
    private String address;
    private String role;

    public Employee() {
    }

    public Employee(String employeeId, String name, String phoneNo, String address, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
