package com.zenpay.paymentgateway.repository;

import com.zenpay.paymentgateway.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionReference(String transactionReference);
}
