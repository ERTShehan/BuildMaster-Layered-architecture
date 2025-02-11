package com.assignment.buildmaster.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MaterialUsage implements Serializable {
    private String usageId;
    private String projectId;
    private String materialId;
    private String quantityUsed;
    private String date;

    public MaterialUsage() {
    }

    public MaterialUsage(String usageId, String projectId, String materialId, String quantityUsed, String date) {
        this.usageId = usageId;
        this.projectId = projectId;
        this.materialId = materialId;
        this.quantityUsed = quantityUsed;
        this.date = date;
    }

    @Override
    public String toString() {
        return "MaterialUsage{" +
                "usageId='" + usageId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", quantityUsed='" + quantityUsed + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
