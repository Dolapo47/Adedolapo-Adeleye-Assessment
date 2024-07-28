package com.zenpay.paymentgateway.controller;

import com.zenpay.paymentgateway.model.Transaction;
import com.zenpay.paymentgateway.response.GatewayResponse;
import com.zenpay.paymentgateway.service.TransactionService;
import com.zenpay.paymentgateway.util.Helper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/payment/gateway")
public class TransactionController {

    private final TransactionService transactionService;
    private final Helper helper;

    public TransactionController(TransactionService transactionService, Helper helper) {
        this.transactionService = transactionService;
        this.helper = helper;
    }

    @PostMapping("/transactions")
    public ResponseEntity<GatewayResponse> createTransaction(@Valid @RequestBody Transaction transaction) {
        GatewayResponse response = transactionService.createTransaction(transaction);
        if (response.getCode().equals("00")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/transactions/{transactionReference}")
    public ResponseEntity<GatewayResponse> getTransaction(@PathVariable String transactionReference) {
        GatewayResponse response = transactionService.getTransaction(transactionReference);
        return helper.getGatewayResponseResponseEntity(response);
    }

    @PutMapping("/webhook/{transactionReference}/status")
    public ResponseEntity<GatewayResponse> updateTransactionStatus(@PathVariable String transactionReference,
                                                               @RequestBody HashMap<String, String> request) {
        GatewayResponse response = transactionService.statusNotificationWebhook(transactionReference, request);
        return helper.getGatewayResponseResponseEntity(response);
    }

}
