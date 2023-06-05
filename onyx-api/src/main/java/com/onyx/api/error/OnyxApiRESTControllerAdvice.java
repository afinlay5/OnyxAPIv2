package com.onyx.api.error;

import com.onyx.commons.exception.BasketballStatisticsNotFoundException;
import com.onyx.commons.exception.OnyxInternalServerErrorException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@RestControllerAdvice
public class OnyxApiRESTControllerAdvice {
    private static final String BAD_REQUEST_TITLE = "Invalid Argument Supplied to OnyxAPI";
    private static final String INTERNAL_SERVER_ERROR_TITLE = "INTERNAL SERVER ERROR";

    /*** 400 *** /
     /**
     * HTTP 400: Bad Request Handler
     * <p> Invalid input supplied to API</p>
     *
     * @param exc               {@link IllegalArgumentException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<OnyxApiProblemDetail> badRequestHandler(IllegalArgumentException exc, ServletWebRequest servletWebRequest) {
        val request = requireHttpServletRequest(servletWebRequest.getRequest());

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withTitle(BAD_REQUEST_TITLE)
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    /*** 400 *** /
     /**
     * HTTP 400: Bad Request Handler
     * <p> Invalid input supplied to API</p>
     *
     * @param exc               {@link MissingRequestHeaderException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<OnyxApiProblemDetail> badRequestHandler(MissingRequestHeaderException exc, ServletWebRequest servletWebRequest) {
        val request = requireHttpServletRequest(servletWebRequest.getRequest());

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withTitle(BAD_REQUEST_TITLE)
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .build(), HttpStatus.BAD_REQUEST);
    }


    /*** 404 *** /
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
        val request = requireHttpServletRequest(servletWebRequest.getRequest());

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withTitle(exc.getTitle())
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withDetail(exc.getDetail())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation(exc.getAdditionalInformation())
                .build(), HttpStatus.NOT_FOUND);
    }

    /*** 500 *** /
     /**
     * HTTP 500: Internal Server Error Handler
     *
     * @param exc               {@link IllegalStateException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<OnyxApiProblemDetail> internalServerErrorHandler(IllegalStateException exc, ServletWebRequest servletWebRequest) {
        val request = requireHttpServletRequest(servletWebRequest.getRequest());

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withTitle(INTERNAL_SERVER_ERROR_TITLE)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation("Some Additional Info")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*** 500 *** /
     /**
     * HTTP 500: Internal Server Error Handler
     *
     * @param exc               {@link IllegalArgumentException} triggering Error Response
     * @param servletWebRequest web context
     * @return {@link ResponseEntity} wrapping RFC 7807 Problem Details response
     */
    @ExceptionHandler(OnyxInternalServerErrorException.class)
    public ResponseEntity<OnyxApiProblemDetail> internalServerErrorHandler(OnyxInternalServerErrorException exc, ServletWebRequest servletWebRequest) {
        val request = requireHttpServletRequest(servletWebRequest.getRequest());

        return new ResponseEntity<>(OnyxApiProblemDetail.newBuilder()
                .withTitle(INTERNAL_SERVER_ERROR_TITLE)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withDetail(exc.getMessage())
                .withInstance(URI.create(request.getContextPath()))
                .withAdditionalInformation("Some Additional Info")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpServletRequest requireHttpServletRequest(HttpServletRequest request) {
        return requireNonNull(request, "request is required but is missing");
    }
}
