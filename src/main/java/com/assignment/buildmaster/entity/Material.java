package com.assignment.buildmaster.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Material implements Serializable {
    private String materialId;
    private String name;
    private String qty;
    private String unit;

    public Material() {
    }

    public Material(String materialId, String name, String qty, String unit) {
        this.materialId = materialId;
        this.name = name;
        this.qty = qty;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId='" + materialId + '\'' +
                ", name='" + name + '\'' +
                ", qty='" + qty + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
