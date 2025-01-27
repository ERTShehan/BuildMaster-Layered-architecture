package com.assignment.buildmaster.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialUsageTM {
    private String usageId;
    private String projectId;
    private String materialId;
    private String quantityUsed;
    private String date;
}
