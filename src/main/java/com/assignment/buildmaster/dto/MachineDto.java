package com.assignment.buildmaster.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MachineDto {
    private String machineId;
    private String name;
    private String status;
    private String description;
}
