package com.ilshan.wallet.service;

import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService{
    private final WalletRepository walletRepository;

    @Override
    public List<Wallet> findAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet saveWallet(UUID id, OperationType operationType, Long amount) {
        return walletRepository.save(new Wallet(id, operationType, amount));
    }

    @Override
    public Optional<Wallet> findWallet(UUID id) {
        return walletRepository.findById(id);
    }
}
