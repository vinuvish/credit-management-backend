package com.interview.creditmanagement.service;

import com.interview.creditmanagement.dto.PaymentPlan;
import com.interview.creditmanagement.dto.Response;
import com.interview.creditmanagement.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

interface CreditManagementService {

    ResponseEntity<ResponseDTO> getPersonDetails();

    String addMonthlyAmortization(Response response, PaymentPlan paymentPlan) throws InterruptedException, ExecutionException;
    String addApplications(Response response) throws InterruptedException, ExecutionException;

    List<Response> getApplications()throws InterruptedException, ExecutionException;

}
