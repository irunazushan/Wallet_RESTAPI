package com.ilshan.wallet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(String message) {
        super(message);
    }
}
