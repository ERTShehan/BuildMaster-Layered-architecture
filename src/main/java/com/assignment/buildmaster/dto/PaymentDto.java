package com.assignment.buildmaster.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private String paymentID;
    private String projectID;
    private String date;
    private String type;
    private String amount;
}