package com.assignment.buildmaster.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineTM {
    private String machineId;
    private String name;
    private String status;
    private String description;
}
