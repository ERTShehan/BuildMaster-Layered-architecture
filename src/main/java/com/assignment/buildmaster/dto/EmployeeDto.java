package com.assignment.buildmaster.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private String employeeId;
    private String name;
    private String phoneNo;
    private String address;
    private String role;
}
