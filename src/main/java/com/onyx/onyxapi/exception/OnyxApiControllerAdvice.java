package com.onyx.onyxapi.exception;

import com.onyx.onyxapi.commons.exception.BasketballStatisticsNotFoundException;
import com.onyx.onyxapi.commons.exception.OnyxInternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OnyxApiControllerAdvice {

    @ExceptionHandler(OnyxInternalServerErrorException.class)
    public ResponseEntity<OnyxApiProblemDetail> internalServerErrorHandler(OnyxInternalServerErrorException exc) {
        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder().build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BasketballStatisticsNotFoundException.class)
    public ResponseEntity<OnyxApiProblemDetail> notFoundHandler(BasketballStatisticsNotFoundException exc) {
        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder().build(), HttpStatus.NOT_FOUND);
    }

}
