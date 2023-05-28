package com.onyx.onyxapi.commons.exception;

public class OnyxInternalServerErrorException extends RuntimeException{
    public OnyxInternalServerErrorException(String cause, Throwable throwable) {
        super(cause, throwable);
    }
}
