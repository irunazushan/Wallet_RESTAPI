package com.ilshan.wallet.controller;

import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.service.WalletServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
