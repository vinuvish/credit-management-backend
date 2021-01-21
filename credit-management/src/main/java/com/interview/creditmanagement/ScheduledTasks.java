package com.interview.creditmanagement;

import com.interview.creditmanagement.dto.PaymentPlan;
import com.interview.creditmanagement.dto.Response;
import com.interview.creditmanagement.dto.ResponseDTO;
import com.interview.creditmanagement.service.CreditManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Component
public class ScheduledTasks {

    @Autowired
    CreditManagementServiceImpl creditManagementService;


    static List<Response> responseList = new ArrayList<>();


    @Scheduled(cron = "0 0 1 * * MON")
    public void scheduleTaskUpdateNewApplications() throws ExecutionException, InterruptedException {
        responseList = creditManagementService.getPersonDetails().getBody().getResponse();
        for (Response response : responseList) {
            String update = creditManagementService.addApplications(response);
            System.out.println(update);
        }


    }

    @Scheduled(fixedRate = 300000)
    public void scheduleTaskAddCreditMonthly() throws ExecutionException, InterruptedException {
        responseList = creditManagementService.getApplications();

        for (Response response : responseList) {
            PaymentPlan paymentPlan ;
            ArrayList<PaymentPlan> PaymentPlanList = response.getPaymentPlans();
            if (PaymentPlanList == null) {
                paymentPlan = CreditPlanCalculation.calculateNextCreditPlan(response);
                creditManagementService.addMonthlyAmortization(response, paymentPlan);
            } else {

                if (PaymentPlanList.size() < response.getPaybackPeriod() && PaymentPlanList.size() != response.getPaybackPeriod()  ) {
                    System.out.println(response.getId());
                    System.out.println(response.getPaybackPeriod());
                    System.out.println(PaymentPlanList.size());
                    paymentPlan = CreditPlanCalculation.calculateNextCreditPlan(response);
                    creditManagementService.addMonthlyAmortization(response, paymentPlan);
                }
            }


        }


    }


}
