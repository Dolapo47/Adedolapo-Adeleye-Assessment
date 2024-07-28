package com.zenpay.paymentgateway.Interface;

import com.zenpay.paymentgateway.model.Transaction;
import com.zenpay.paymentgateway.response.GatewayResponse;

import java.util.HashMap;

public interface IGateway {

    GatewayResponse createTransaction(Transaction transaction);
    GatewayResponse getTransaction(String transactionReference);
    GatewayResponse statusNotificationWebhook(String id, HashMap<String, String> request);
}
