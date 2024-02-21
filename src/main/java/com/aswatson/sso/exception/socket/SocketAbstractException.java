package com.aswatson.sso.exception.socket;

import com.aswatson.sso.data.constant.ResponseCode;

public abstract class SocketAbstractException extends RuntimeException {
    public SocketAbstractException(String message, ResponseCode responseCode) {
        super(message);
    }

}
