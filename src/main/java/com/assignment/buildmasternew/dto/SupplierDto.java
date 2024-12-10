package com.assignment.buildmasternew.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDto {
    private String supplierId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
}
