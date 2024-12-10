package com.assignment.buildmasternew.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectExpensesTM {
    private String projectID;
    private String projectName;
    private double totalExpenses;
    private double totalMaterialCost;
    private double projectTotalCost;
}
