package com.ilshan.wallet.controller;

import com.ilshan.wallet.controller.util.ErrorResponse;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.exceptions.WalletNotFoundException;
import com.ilshan.wallet.service.WalletServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wallets")
@Transactional(readOnly = true)
public class WalletsController {
    private final WalletServiceImpl walletService;

    @GetMapping("/{walletId}")
    public ResponseEntity<Long> getWalletBalance(@PathVariable("walletId") UUID walletId) {
        Wallet wallet = walletService.findWallet(walletId)
                .orElseThrow(() -> new WalletNotFoundException("wallet with id " + walletId + " not found"));
        Long balance = walletService.getWalletBalance(walletId);

        return ResponseEntity.ok(balance);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(WalletNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
