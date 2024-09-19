package com.ilshan.wallet.service;

import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.exceptions.InsufficientFundsException;
import com.ilshan.wallet.exceptions.InvalidOperationTypeException;
import com.ilshan.wallet.exceptions.WalletNotFoundException;
import com.ilshan.wallet.repository.TransactionRepository;
import com.ilshan.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<Wallet> findAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet saveWallet(UUID id) {
        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setBalance(0L);
        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findWallet(UUID id) {
        return walletRepository.findById(id);
    }

    @Override
    public Transaction performTransaction(UUID walletId, OperationType operationType, Long amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with ID " + walletId + " not found"));

        if (operationType == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance() + amount);
        } else if (operationType == OperationType.WITHDRAW) {
            if (wallet.getBalance() < amount) {
                throw new InsufficientFundsException("Insufficient funds in wallet with ID " + walletId);
            }
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new InvalidOperationTypeException("Invalid operation type: " + operationType);
        }

        walletRepository.save(wallet);

        return createTransaction(wallet, operationType, amount);
    }

    @Override
    public Long getWalletBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(Wallet::getBalance)
                .orElse(null);
    }

    private Transaction createTransaction(Wallet wallet, OperationType operationType, Long amount) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setOperationType(operationType);
        transaction.setAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
