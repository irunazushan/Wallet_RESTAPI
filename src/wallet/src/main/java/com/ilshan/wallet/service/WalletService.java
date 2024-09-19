package com.ilshan.wallet.service;

import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    List<Wallet> findAllWallets();

    Wallet saveWallet(UUID id);

    Optional<Wallet> findWallet(UUID id);

    Transaction performTransaction(UUID walletId, OperationType operationType, Long amount);

    Long getWalletBalance(UUID walletId);
}
