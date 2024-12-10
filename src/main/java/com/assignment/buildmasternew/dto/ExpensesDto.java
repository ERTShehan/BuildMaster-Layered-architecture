package com.assignment.buildmasternew.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpensesDto {
    private String expenseId;
    private String type;
    private double amount;
    private String date;
    private String projectId;
    private String employeeId;
}
