package com.ilshan.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilshan.wallet.dto.TransactionRequest;
import com.ilshan.wallet.dto.WalletRequest;
import com.ilshan.wallet.entity.OperationType;
import com.ilshan.wallet.entity.Transaction;
import com.ilshan.wallet.entity.Wallet;
import com.ilshan.wallet.exceptions.InsufficientFundsException;
import com.ilshan.wallet.exceptions.InvalidOperationTypeException;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BadRequestControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletServiceImpl walletService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHandleWalletNotFoundException() throws Exception {
        String walletId = "72f561a8-16da-4ef8-851c-f1f7394b0ad3";
        String expectedMessage = "Wallet with ID " + walletId + " not found";

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void testHandleBindException() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invalidField\":\"value\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testHandleInsufficientFundsException() throws Exception {
        UUID walletId = UUID.randomUUID();
        String expectedMessage = "Insufficient funds in wallet with ID " + walletId;

        when(walletService.performTransaction(any(UUID.class), any(OperationType.class), anyLong())).
                thenThrow(new InsufficientFundsException(expectedMessage));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TransactionRequest(
                        walletId,
                        OperationType.WITHDRAW,
                        5000L
                ))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void testHandleInvalidOperationTypeException() throws Exception {
        UUID walletId = UUID.randomUUID();
        String operationType = "NOT_EXISTS_TYPE";
        String expectedMessage = "Invalid operation type: " + operationType;

        when(walletService.performTransaction(any(UUID.class), any(OperationType.class), anyLong())).
                thenThrow(new InvalidOperationTypeException(expectedMessage));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TransactionRequest(
                                walletId,
                                OperationType.WITHDRAW,
                                5000L
                        ))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    public void testHandleHttpMessageNotReadable() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid argument type"));
    }

    @Test
    public void testHandleGenericException() throws Exception {
        mockMvc.perform(post("/api/v1/not_correct")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred."));
    }
}
