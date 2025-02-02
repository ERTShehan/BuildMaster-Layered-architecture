package com.assignment.buildmaster.entity;

import java.io.Serializable;

public class Machine implements Serializable {
    private String machineId;
    private String name;
    private String status;
    private String description;

    public Machine() {
    }

    public Machine(String machineId, String name, String status, String description) {
        this.machineId = machineId;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
