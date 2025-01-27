package com.assignment.buildmaster.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialBuyTM {
    private String paymentId;
    private String materialId;
    private String supplierId;
    private Date date;
    private double unitAmount;
    private String quantity;
    private double totalPrice;
}
