package com.ilshan.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
    @NotNull(message = "Wallet ID cannot be null")
    @Size(min = 36, max = 36, message = "Incorrect UUID size")
    private String walletId;
}
