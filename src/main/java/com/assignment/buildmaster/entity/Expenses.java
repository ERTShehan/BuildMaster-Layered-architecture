package com.assignment.buildmaster.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
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
