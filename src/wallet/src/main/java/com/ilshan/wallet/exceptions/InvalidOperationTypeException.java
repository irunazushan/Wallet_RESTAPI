package com.ilshan.wallet.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidOperationTypeException extends RuntimeException{
    public InvalidOperationTypeException(String message) {
        super(message);
    }
}
