package com.onyx.onyxapi.commons.exception;

public class BasketballStatisticsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BasketballStatisticsNotFoundException(String excStr) {
        super(excStr);
    }
}
