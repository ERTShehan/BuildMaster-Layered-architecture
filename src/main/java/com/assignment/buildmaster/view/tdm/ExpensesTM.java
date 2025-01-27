package com.assignment.buildmaster.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesTM {
    private String expenseId;
    private String type;
    private double amount;
    private String date;
    private String projectId;
    private String employeeId;
}
