package com.zenpay.paymentgateway.model;

import com.zenpay.paymentgateway.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
    @Builder.Default
    private String status = TransactionStatus.PENDING.toString();
    @NotNull(message = "Transaction reference cannot be null")
    @NotEmpty(message = "Provide transaction reference")
    @Column(unique = true)
    private String transactionReference;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
