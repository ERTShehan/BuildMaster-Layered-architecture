package com.assignment.buildmaster.entity;

import java.io.Serializable;

public class Payment implements Serializable {
    private String paymentID;
    private String projectID;
    private String date;
    private String type;
    private String amount;

    public Payment() {
    }

    public Payment(String paymentID, String projectID, String date, String type, String amount) {
        this.paymentID = paymentID;
        this.projectID = projectID;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", projectID='" + projectID + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
