package com.ilshan.wallet.controller;

import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.service.WalletServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wallet")
@Transactional(readOnly = true)
public class WalletController {
    private final WalletServiceImpl walletService;

    @GetMapping
    public List<Wallet> getWallets() {
        return walletService.findAllWallets();
    }

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet(@RequestBody UUID walletId) {
        Wallet newWallet = walletService.saveWallet(walletId);
        return ResponseEntity.status(201).body(newWallet);
    }

    @PostMapping
    public ResponseEntity<Transaction> performTransaction(@RequestBody TransactionRequest transactionRequest) {
        // Call the service to perform the transaction
        Transaction transaction = walletService.performTransaction(
                transactionRequest.getWalletId(),
                transactionRequest.getOperationType(),
                transactionRequest.getAmount()
        );
        return ResponseEntity.ok(transaction); // Return the created transaction
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Long> getWalletBalance(@PathVariable UUID walletId) {
        Long balance = walletService.getWalletBalance(walletId);
        if (balance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }
}
