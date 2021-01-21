package com.interview.creditmanagement.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.interview.creditmanagement.dto.PaymentPlan;
import com.interview.creditmanagement.dto.Response;
import com.interview.creditmanagement.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class CreditManagementServiceImpl implements CreditManagementService {

    public static final String COL_NAME = "paymentPlan";

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

    @Override
    public ResponseEntity<ResponseDTO> getPersonDetails() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Username", "interview");
        headers.set("Password", "Hgn7epI0hg1wS");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ResponseDTO> response = null;

        response = restTemplate.exchange("https://interview.brixo.se/api/application",
                HttpMethod.GET, request, new ParameterizedTypeReference<ResponseDTO>() {
                },
                ResponseDTO.class);

        return response;
    }


    @Override
    public String addApplications(Response response) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(Integer.toString(response.getId())).set(response, SetOptions.merge());
        return collectionsApiFuture.get().getUpdateTime().toString();

    }

    @Override
    public String addMonthlyAmortization(Response response, PaymentPlan paymentPlan) throws InterruptedException, ExecutionException {
        Map<String, Object> docData = new HashMap<>();
        docData.put("paymentPlans", FieldValue.arrayUnion(paymentPlan));
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(Integer.toString(response.getId())).set(docData, SetOptions.merge());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public List<Response> getApplications() throws InterruptedException, ExecutionException {
        List<Response> responseList = new ArrayList<>();

        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(COL_NAME).whereEqualTo("status", "approved").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            Response response = new Response();
            response = document.toObject(Response.class);
            responseList.add(response);
            System.out.println(document.getId() + " => " + document.toObject(Response.class));
        }
        return responseList;

    }
}
