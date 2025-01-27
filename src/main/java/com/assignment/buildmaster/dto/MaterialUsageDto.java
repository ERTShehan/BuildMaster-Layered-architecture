package com.assignment.buildmaster.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialUsageDto {
    private String usageId;
    private String projectId;
    private String materialId;
    private String quantityUsed;
    private String date;
}
