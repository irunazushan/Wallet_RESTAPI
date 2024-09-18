package com.ilshan.wallet.dto;

import com.ilshan.wallet.entity.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private UUID walletId;
    private OperationType operationType; // Ensure this is the correct type
    private Long amount;
}
