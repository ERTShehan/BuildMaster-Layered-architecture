package com.assignment.buildmasternew.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaterialBuyDto {
    private String paymentId;
    private String materialId;
    private String supplierId;
    private Date date;
    private double unitAmount;
    private String quantity;
    private double totalPrice;
}
