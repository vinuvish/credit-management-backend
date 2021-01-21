package com.interview.creditmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentPlan {
    private String plan;
    private  String amortization;
    private String interest;
    private String invoiceFee;
    private String totalPay;
    private String debtBalance;


}
