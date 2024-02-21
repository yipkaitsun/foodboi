package com.aswatson.sso.exception.socket;


import com.aswatson.sso.data.constant.ResponseCode;

public class SocketException extends SocketAbstractException {
    public SocketException(String message, ResponseCode responseCode) {
        super(message, responseCode);
    }

    public SocketException() {
        super("Internal Server Error", ResponseCode.SERVER_ERROR);
    }

    public static SocketException BUIDNotFoundException() {
        return new SocketException("The BuId is not exist or disabled", ResponseCode.VALIDATION_BUID_NOT_VALID);
    }
}
