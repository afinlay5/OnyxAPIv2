package com.onyx.onyxapi.exception;

import com.onyx.onyxapi.commons.exception.BasketballStatisticsNotFoundException;
import com.onyx.onyxapi.commons.exception.OnyxInternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@RestControllerAdvice
public class OnyxApiControllerAdvice {

    //TODO - Properly configure

    /**
     * HTTP 500: Internal Server Error Handler
     *
     * @param exc               {@link IllegalArgumentException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(OnyxInternalServerErrorException.class)
    public ResponseEntity<OnyxApiProblemDetail> internalServerErrorHandler(OnyxInternalServerErrorException exc, ServletWebRequest servletWebRequest) {
        var request = requireNonNull(servletWebRequest.getRequest(), "request is required but is missing");

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withType(URI.create("/type"))
                .withTitle("Title")
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation("Some Additional Info")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * HTTP 404: Not Found Error Handler
     * <p> Basketball Statistic were not found </p>
     *
     * @param exc               {@link IllegalArgumentException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(BasketballStatisticsNotFoundException.class)
    public ResponseEntity<OnyxApiProblemDetail> notFoundHandler(BasketballStatisticsNotFoundException exc, ServletWebRequest servletWebRequest) {
        var request = requireNonNull(servletWebRequest.getRequest(), "request is required but is missing");

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withType(URI.create("/type"))
                .withTitle("Title")
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation("Some Additional Info")
                .build(), HttpStatus.NOT_FOUND);
    }

    /**
     * HTTP 404: Bad Request Handler
     * <p> Invalid input supplied to API</p>
     *
     * @param exc               {@link IllegalArgumentException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(BasketballStatisticsNotFoundException.class)
    public ResponseEntity<OnyxApiProblemDetail> notFoundHandler(IllegalArgumentException exc, ServletWebRequest servletWebRequest) {
        var request = requireNonNull(servletWebRequest.getRequest(), "request is required but is missing");

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withType(URI.create("/type"))
                .withTitle("Title")
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation("Some Additional Info")
                .build(), HttpStatus.BAD_REQUEST);
    }



}
