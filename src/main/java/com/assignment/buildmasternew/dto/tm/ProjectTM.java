package com.assignment.buildmasternew.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProjectTM {
    private String projectId;
    private String projectName;
    private String startDate;
    private String endDate;
    private String projectType;
    private String status;
    private String clientId;
}
