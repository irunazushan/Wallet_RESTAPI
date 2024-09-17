package com.ilshan.wallet.service;

import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    List<Wallet> findAllWallets();

    Wallet saveWallet(UUID id, OperationType operationType, Long amount);

    Optional<Wallet> findWallet(UUID id);
}
