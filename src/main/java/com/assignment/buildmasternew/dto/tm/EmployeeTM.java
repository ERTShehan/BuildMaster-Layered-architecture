package com.assignment.buildmasternew.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTM {
    private String employeeId;
    private String name;
    private String phoneNo;
    private String address;
    private String role;
}
