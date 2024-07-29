package com.paymentgateway.Interface;

import com.paymentgateway.model.Transaction;
import com.paymentgateway.response.GatewayResponse;

import java.util.HashMap;

public interface IGateway {

    GatewayResponse createTransaction(Transaction transaction);
    GatewayResponse getTransactionStatus(String transactionReference);
    GatewayResponse statusNotificationWebhook(String id, HashMap<String, String> request);
}
