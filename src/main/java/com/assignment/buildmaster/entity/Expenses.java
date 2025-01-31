package com.assignment.buildmaster.entity;

import java.io.Serializable;

public class Expenses implements Serializable {
    private String expenseId;
    private String type;
    private double amount;
    private String date;
    private String projectId;
    private String employeeId;

    public Expenses() {
    }

    public Expenses(String expenseId, String type, double amount, String date, String projectId, String employeeId) {
        this.expenseId = expenseId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "expenseId='" + expenseId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", projectId='" + projectId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
