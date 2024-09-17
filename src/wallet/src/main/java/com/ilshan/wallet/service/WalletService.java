package com.ilshan.wallet.service;

import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    List<Wallet> findAllWallets();

    // Save a new wallet (if needed)
    Wallet saveWallet(UUID id);

    // Find a wallet by its UUID
    Optional<Wallet> findWallet(UUID id);

    Transaction performTransaction(UUID walletId, OperationType operationType, Long amount);

    // Get the balance of a wallet by its UUID
    Long getWalletBalance(UUID walletId);
}
