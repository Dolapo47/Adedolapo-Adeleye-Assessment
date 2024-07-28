package com.paymentgateway.repository;

import com.paymentgateway.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionReference(String transactionReference);
}
