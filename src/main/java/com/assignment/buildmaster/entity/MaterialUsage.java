package com.assignment.buildmaster.entity;

import java.io.Serializable;

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

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(String quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
