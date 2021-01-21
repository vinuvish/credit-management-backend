package com.interview.creditmanagement;

import com.interview.creditmanagement.service.CreditManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
public class Controller {

    @Autowired
    CreditManagementServiceImpl creditManagementService;

    @GetMapping("/get")
    public Object getListChargePayment(HttpServletRequest request) throws Exception {
        return creditManagementService.getPersonDetails().getBody();
    }




}
