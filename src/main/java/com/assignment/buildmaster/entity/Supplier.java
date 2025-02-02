package com.assignment.buildmaster.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Supplier implements Serializable {
    private String supplierId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;

    public Supplier() {
    }

    public Supplier(String supplierId, String name, String address, String phoneNo, String email) {
        this.supplierId = supplierId;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
    }

}
