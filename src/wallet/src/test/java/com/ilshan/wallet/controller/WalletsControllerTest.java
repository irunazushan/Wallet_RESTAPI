package com.ilshan.wallet.controller;

import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletServiceImpl walletService;

    @Test
    public void testGetWalletBalance_Success() throws Exception {
        UUID walletId = UUID.randomUUID();
        Long expectedBalance = 100L;
        Wallet wallet = new Wallet(walletId, expectedBalance);

        when(walletService.findWallet(walletId)).thenReturn(Optional.of(wallet));
        when(walletService.getWalletBalance(walletId)).thenReturn(expectedBalance);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedBalance.toString()));
    }

    @Test
    public void testGetWalletBalance_NotFound() throws Exception {
        UUID walletId = UUID.randomUUID();

        when(walletService.findWallet(walletId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
