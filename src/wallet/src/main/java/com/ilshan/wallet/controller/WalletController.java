package com.ilshan.wallet.controller;

import com.ilshan.wallet.controller.util.ErrorResponse;
import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.dto.TransactionResponse;
import com.ilshan.wallet.dto.WalletRequest;
import com.ilshan.wallet.dto.WalletResponse;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.exceptions.InsufficientFundsException;
import com.ilshan.wallet.exceptions.InvalidOperationTypeException;
import com.ilshan.wallet.exceptions.WalletNotFoundException;
import com.ilshan.wallet.service.WalletServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public List<WalletResponse> getWallets() {
        List<Wallet> wallets = walletService.findAllWallets();
        List<WalletResponse> walletResponses = wallets.stream().map(
                wallet -> new WalletResponse(wallet.getId(), wallet.getBalance()))
                .collect(Collectors.toList());
        return walletResponses;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletRequest walletRequest) {
        UUID walletId = UUID.fromString(walletRequest.getWalletId());
        Wallet newWallet = walletService.saveWallet(walletId);
        return ResponseEntity.status(201).body(newWallet);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TransactionResponse> performTransaction(@RequestBody TransactionRequest transactionRequest) {
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

    @ExceptionHandler(WalletNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    private ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationTypeException.class)
    private ResponseEntity<ErrorResponse> handleInvalidOperationTypeException(InvalidOperationTypeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid operation type. You can use just WITHDRAW or DEPOSIT", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
