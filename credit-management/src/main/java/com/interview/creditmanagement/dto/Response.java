package com.interview.creditmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Response {

    public int id;
    public String firstName;
    public String lastName;
    public String ssn;
    public String phone;
    public String email;
    public String loanType;
    public String approvedAmount;
    public int paybackPeriod;
    public String interestRate;
    public String invoiceFee;
    public String status;
    public String created_at;
    public String updated_at;
    public ArrayList<PaymentPlan> paymentPlans;

}
