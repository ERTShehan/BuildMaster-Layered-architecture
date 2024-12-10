package com.assignment.buildmasternew.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDto {
    private String projectId;
    private String projectName;
    private String startDate;
    private String endDate;
    private String projectType;
    private String status;
    private String clientId;
}
