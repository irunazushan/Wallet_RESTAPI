package com.ilshan.wallet.exceptions;

import com.ilshan.wallet.controller.WalletsController;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(String message) {
        super(message);
    }
}
