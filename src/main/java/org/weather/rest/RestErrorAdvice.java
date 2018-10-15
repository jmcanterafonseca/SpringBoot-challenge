package org.weather.rest;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.weather.errors.ApiErrorData;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestErrorAdvice {
    public class RestExceptionHandler extends ResponseEntityExceptionHandler {
        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            String error = ex.getMessage();
            return new ResponseEntity<Object>(new ApiErrorData(HttpStatus.BAD_REQUEST.value(), error),HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            String error = ex.getMessage();
            return new ResponseEntity<Object>(new ApiErrorData(HttpStatus.BAD_REQUEST.value(), error),HttpStatus.BAD_REQUEST);
        }

        @Override
        protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            String error = ex.getMessage();
            return new ResponseEntity<Object>(new ApiErrorData(HttpStatus.BAD_REQUEST.value(), error),HttpStatus.BAD_REQUEST);
        }

        protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            String error = ex.getMessage();
            return new ResponseEntity<Object>(new ApiErrorData(HttpStatus.BAD_REQUEST.value(), error),HttpStatus.BAD_REQUEST);
        }
    }
}
