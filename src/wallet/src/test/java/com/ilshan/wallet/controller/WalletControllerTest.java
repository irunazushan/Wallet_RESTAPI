package com.ilshan.wallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;


public class WalletControllerTest {

    /*
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    public void testGetWallets() throws Exception {
        Wallet wallet1 = new Wallet();
        wallet1.setId(UUID.randomUUID());
        wallet1.setBalance(1000L);

        when(walletService.findAllWallets()).thenReturn(Arrays.asList(wallet1));

        mockMvc.perform(get("/api/v1/wallet"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].balance").value(1000));
    }

    @Test
    public void testPerformTransaction() throws Exception {
        UUID walletId = UUID.randomUUID();
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(walletId);
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(1000L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setWallet(new Wallet());
        transaction.setOperationType(OperationType.DEPOSIT);
        transaction.setAmount(1000L);
        transaction.setCreatedAt(LocalDateTime.now());

        when(walletService.performTransaction(any(UUID.class), any(OperationType.class), anyLong())).thenReturn(transaction);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + walletId + "\",\"operationType\":\"DEPOSIT\",\"amount\":1000}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.operationType").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(1000));
    }

    @Test
    public void testGetWalletBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        Long balance = 1000L;

        when(walletService.getWalletBalance(walletId)).thenReturn(balance);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1000"));
    }

    @Test
    public void testCreateWallet() throws Exception {
        UUID walletId = UUID.randomUUID();
        Wallet newWallet = new Wallet();
        newWallet.setId(walletId);
        newWallet.setBalance(0L);

        when(walletService.saveWallet(any(UUID.class))).thenReturn(newWallet);

        mockMvc.perform(post("/api/v1/wallet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + walletId + "\""))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(0));
    }

     */
}
