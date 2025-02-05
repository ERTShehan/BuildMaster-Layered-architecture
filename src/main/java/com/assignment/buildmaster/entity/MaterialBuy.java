package com.assignment.buildmaster.entity;

import java.io.Serializable;
import java.sql.Date;

public class MaterialBuy implements Serializable {
    private String paymentId;
    private String materialId;
    private String supplierId;
    private Date date;
    private double unitAmount;
    private String quantity;
    private double totalPrice;

    public MaterialBuy() {
    }

    public MaterialBuy(String paymentId, String materialId, String supplierId, Date date, double unitAmount, String quantity, double totalPrice) {
        this.paymentId = paymentId;
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.date = date;
        this.unitAmount = unitAmount;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "MaterialBuy{" +
                "paymentId='" + paymentId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", date=" + date +
                ", unitAmount=" + unitAmount +
                ", quantity='" + quantity + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
