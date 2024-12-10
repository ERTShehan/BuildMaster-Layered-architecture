package com.assignment.buildmasternew.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialDto {
    private String materialId;
    private String name;
    private String qty;
    private String unit;
}
