package com.ilshan.wallet.controller;

import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.dto.TransactionResponse;
import com.ilshan.wallet.dto.WalletRequest;
import com.ilshan.wallet.dto.WalletResponse;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.service.WalletServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wallet")
@Transactional(readOnly = true)
public class WalletController {
    private final WalletServiceImpl walletService;

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getWallets() {
        List<Wallet> wallets = walletService.findAllWallets();
        List<WalletResponse> walletResponses = wallets.stream().map(
                wallet -> new WalletResponse(wallet.getId(), wallet.getBalance()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(walletResponses);
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<WalletResponse> createWallet(@RequestBody WalletRequest walletRequest) {
        UUID walletId = UUID.fromString(walletRequest.getWalletId());
        Wallet newWallet = walletService.saveWallet(walletId);
        WalletResponse responseWallet = new WalletResponse(newWallet.getId(), newWallet.getBalance());
        return ResponseEntity.status(201).body(responseWallet);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TransactionResponse> performTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest,
            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Transaction transaction = walletService.performTransaction(
                    transactionRequest.getWalletId(),
                    transactionRequest.getOperationType(),
                    transactionRequest.getAmount()
            );
            TransactionResponse transactionResponse = new TransactionResponse(
                    transaction.getWallet().getId(),
                    transaction.getOperationType(),
                    transaction.getAmount(),
                    transaction.getCreatedAt());
            return ResponseEntity.ok(transactionResponse);
        }
    }
}
