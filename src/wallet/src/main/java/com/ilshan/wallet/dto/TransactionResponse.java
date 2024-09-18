package com.ilshan.wallet.dto;

import com.ilshan.wallet.entity.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private UUID walletId;
    private OperationType operationType; // Ensure this is the correct type
    private Long amount;
    private LocalDateTime createdAt;
}
