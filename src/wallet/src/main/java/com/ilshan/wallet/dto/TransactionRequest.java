package com.ilshan.wallet.dto;

import com.ilshan.wallet.entity.OperationType;
import lombok.Data;
import java.util.UUID;

@Data
public class TransactionRequest {
    private UUID walletId;
    private OperationType operationType; // Ensure this is the correct type
    private Long amount;
}
