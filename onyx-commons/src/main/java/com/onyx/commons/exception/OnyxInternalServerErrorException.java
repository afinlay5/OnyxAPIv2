package com.onyx.commons.exception;

// @ResponseStatus is still not great here.
public final class OnyxInternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OnyxInternalServerErrorException(String excStr, Throwable cause) {
        super(excStr, cause);
    }
}
