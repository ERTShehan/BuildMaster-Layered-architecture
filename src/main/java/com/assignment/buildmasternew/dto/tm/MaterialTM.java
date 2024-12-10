package com.assignment.buildmasternew.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialTM {
    private String materialId;
    private String name;
    private String qty;
    private String unit;
}
