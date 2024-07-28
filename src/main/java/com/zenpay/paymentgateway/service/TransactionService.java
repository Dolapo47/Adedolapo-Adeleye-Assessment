package com.zenpay.paymentgateway.service;

import com.zenpay.paymentgateway.Interface.IGateway;
import com.zenpay.paymentgateway.model.Transaction;
import com.zenpay.paymentgateway.repository.TransactionRepository;
import com.zenpay.paymentgateway.response.GatewayResponse;
import com.zenpay.paymentgateway.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TransactionService implements IGateway {
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final Helper helper;

    public TransactionService(TransactionRepository transactionRepository, Helper helper) {
        this.transactionRepository = transactionRepository;
        this.helper = helper;
    }

    @Override
    public GatewayResponse createTransaction(Transaction transaction) {
        Transaction savedTransaction;
        try {
            savedTransaction = transactionRepository.save(transaction);
            if(savedTransaction != null) {
                return GatewayResponse.builder()
                        .code("00")
                        .message("Transaction successfully sent")
                        .data(savedTransaction)
                        .build();
            }
            return GatewayResponse.builder()
                    .code("99")
                    .message("Transaction could not be sent")
                    .build();
        } catch (DataIntegrityViolationException ex) {
            logger.info(ex.getMessage());
            return GatewayResponse.builder()
                    .code("99")
                    .message("Unique constraint violation found")
                    .build();
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return GatewayResponse.builder()
                    .code("99")
                    .message("Transaction could not be sent")
                    .build();
        }
    }

    @Override
    public GatewayResponse getTransaction(String transactionReference) {
        Transaction savedTransaction = transactionRepository.findByTransactionReference(transactionReference);
        try {
            if(savedTransaction != null) {
                return GatewayResponse.builder()
                        .code("00")
                        .message("Transaction successfully Retrieved")
                        .data(savedTransaction)
                        .build();
            }
            return GatewayResponse.builder()
                    .code("90")
                    .message("Transaction does not exist")
                    .build();
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            return GatewayResponse.builder()
                    .code("99")
                    .message("Unable to retrieve transaction")
                    .build();
        }

    }

    @Override
    public GatewayResponse statusNotificationWebhook(String transactionReference, HashMap<String, String> request) {
        try {
            boolean isStatus = helper.validateTransactionStatus(request.get("status"));
            if(!isStatus){
                return GatewayResponse.builder()
                        .code("90")
                        .message("Invalid transaction status passed")
                        .build();
            }
            Transaction transaction = transactionRepository.findByTransactionReference(transactionReference);
            if (transaction != null) {
                transaction.setStatus(request.get("status"));
                Transaction updatedTransaction = transactionRepository.save(transaction);
                return GatewayResponse.builder()
                        .code("00")
                        .message("Transaction successfully updated")
                        .data(updatedTransaction)
                        .build();
            }
            return GatewayResponse.builder()
                    .code("90")
                    .message("Transaction does not exist")
                    .build();
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            return GatewayResponse.builder()
                    .code("99")
                    .message("Unable to update transaction")
                    .build();
        }

    }
}
