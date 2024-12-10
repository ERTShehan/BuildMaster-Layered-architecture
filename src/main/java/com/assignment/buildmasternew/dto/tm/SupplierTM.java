package com.assignment.buildmasternew.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTM {
    private String supplierId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
}
