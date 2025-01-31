package com.assignment.buildmaster.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Project implements Serializable {
    private String projectId;
    private String projectName;
    private String startDate;
    private String endDate;
    private String projectType;
    private String status;
    private String clientId;

    public Project() {
    }

    public Project(String projectId, String projectName, String startDate, String endDate, String projectType, String status, String clientId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectType = projectType;
        this.status = status;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", projectType='" + projectType + '\'' +
                ", status='" + status + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
