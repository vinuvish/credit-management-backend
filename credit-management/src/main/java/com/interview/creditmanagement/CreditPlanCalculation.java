package com.interview.creditmanagement;

import com.interview.creditmanagement.dto.PaymentPlan;
import com.interview.creditmanagement.dto.Response;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class CreditPlanCalculation {
    static DecimalFormat f = new DecimalFormat("#,###,###");
    public static ArrayList<PaymentPlan> calculateAllCreditPlan(Response response) {

        ArrayList<PaymentPlan> PaymentPlanList = new ArrayList<>();

        double new_balance = 0.0;
        double interest_paid = 0.0, principle_paid = 0.0, amortization = 0.0;

        double ending_balance = Double.parseDouble(response.approvedAmount);
        int paybackPeriod = response.paybackPeriod;
        double interestRate = Double.parseDouble(response.interestRate);
        double invoiceFee = Double.parseDouble(response.invoiceFee);


        amortization = (ending_balance / paybackPeriod);

        for (int i = 1; i <= paybackPeriod; i++) {
            PaymentPlan paymentPlan = new PaymentPlan();
            new_balance = ending_balance;
            // Calculate interest by multiplying rate against balance
            interest_paid = (new_balance * (interestRate / 12.0)) / 100;
            // Subtract interest from your payment
            principle_paid = amortization + interest_paid;
            // Subtract final payment from running balance
            ending_balance = new_balance - amortization;


            paymentPlan.setPlan(Integer.toString(i));
            paymentPlan.setAmortization(f.format(amortization));
            paymentPlan.setInterest(f.format(interest_paid));
            paymentPlan.setInvoiceFee(f.format(invoiceFee));
            paymentPlan.setTotalPay(f.format(principle_paid + invoiceFee));
            paymentPlan.setDebtBalance(f.format(ending_balance));

            System.out.println(i + "" +
                    ". Amortization: " + f.format(amortization) +
                    " Interest: " + f.format(interest_paid) +
                    " InvoiceFee: " + f.format(invoiceFee) +
                    " TotalPay: " + f.format(invoiceFee + principle_paid) +
                    " Principle: " + f.format(principle_paid) +
                    " Loan Balance is: " + f.format(ending_balance));

            PaymentPlanList.add(paymentPlan);
        }


        return PaymentPlanList;
    }
    public static PaymentPlan calculateNextCreditPlan(Response response) {

        ArrayList<PaymentPlan> PaymentPlanList = response.getPaymentPlans();

        PaymentPlan paymentPlan = new PaymentPlan();
        double new_balance = 0.0;
        double interest_paid = 0.0, principle_paid = 0.0, amortization = 0.0;

        double ending_balance = Double.parseDouble(response.approvedAmount);
        int paybackPeriod = response.paybackPeriod;
        double interestRate = Double.parseDouble(response.interestRate);
        double invoiceFee = Double.parseDouble(response.invoiceFee);

        amortization = (ending_balance / paybackPeriod);

        if(PaymentPlanList == null) {
            new_balance = ending_balance;
            paymentPlan.setPlan( "1");
        }
        else{
            PaymentPlan lastPaymentPlan = PaymentPlanList.get(PaymentPlanList.size() -1);
            new_balance = Double.parseDouble( lastPaymentPlan.getDebtBalance()) -invoiceFee;
            int newPlan = Integer.parseInt(lastPaymentPlan.getPlan());
            paymentPlan.setPlan( Integer.toString( newPlan + 1));
        }
        interest_paid = (new_balance * (interestRate / 12.0)) / 100;
        principle_paid = amortization + interest_paid;
        ending_balance =Integer.parseInt(paymentPlan.getPlan()) ==  response.paybackPeriod ? 0.0 :  new_balance - amortization;

        paymentPlan.setAmortization(f.format(amortization));
        paymentPlan.setInterest(f.format(interest_paid));
        paymentPlan.setInvoiceFee(f.format(invoiceFee));
        paymentPlan.setTotalPay(f.format(principle_paid + invoiceFee));
        paymentPlan.setDebtBalance(f.format(ending_balance).replace(",",""));
//        System.out.println(1 + "" +
//                ". Amortization: " + f.format(amortization) +
//                " Interest: " + f.format(interest_paid) +
//                " InvoiceFee: " + f.format(invoiceFee) +
//                " TotalPay: " + f.format(invoiceFee + principle_paid) +
//                " Principle: " + f.format(principle_paid) +
//                " Loan Balance is: " + f.format(ending_balance));


        return paymentPlan;
    }
}
