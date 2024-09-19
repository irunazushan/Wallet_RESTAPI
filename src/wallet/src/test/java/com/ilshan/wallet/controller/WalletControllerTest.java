package com.ilshan.wallet.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.dto.WalletRequest;
import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
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
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WalletServiceImpl walletService;

    @Test
    public void testGetWallets() throws Exception {
        Wallet wallet = new Wallet(UUID.randomUUID(), 100L);
        when(walletService.findAllWallets()).thenReturn(Collections.singletonList(wallet));

        mockMvc.perform(get("/api/v1/wallet"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].walletId").value(wallet.getId().toString()))
                .andExpect(jsonPath("$[0].balance").value(wallet.getBalance()));
    }


    @Test
    public void testCreateWallet() throws Exception {

        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setWalletId(UUID.randomUUID().toString());
        Wallet newWallet = new Wallet(UUID.fromString(walletRequest.getWalletId()), 0L);
        when(walletService.saveWallet(any(UUID.class))).thenReturn(newWallet);

        mockMvc.perform(post("/api/v1/wallet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.walletId").value(newWallet.getId().toString()))
                .andExpect(jsonPath("$.balance").value(newWallet.getBalance()));
    }


    @Test
    public void testPerformTransactionDeposit() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setWalletId(UUID.randomUUID());
        transactionRequest.setOperationType(OperationType.DEPOSIT);
        transactionRequest.setAmount(50L);

        Transaction transaction = new Transaction();
        transaction.setWallet(new Wallet(transactionRequest.getWalletId(), 100L));
        transaction.setOperationType(transactionRequest.getOperationType());
        transaction.setAmount(transactionRequest.getAmount());

        when(walletService.performTransaction(any(UUID.class), any(OperationType.class), anyLong())).thenReturn(transaction);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.walletId").value(transaction.getWallet().getId().toString()))
                .andExpect(jsonPath("$.operationType").value(transaction.getOperationType().toString()))
                .andExpect(jsonPath("$.amount").value(transaction.getAmount()));
    }
}
