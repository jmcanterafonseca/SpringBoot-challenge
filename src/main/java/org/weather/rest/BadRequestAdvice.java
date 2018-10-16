package org.weather.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.weather.errors.ApiErrorData;
import org.weather.errors.BadRequestError;

@ControllerAdvice
public class BadRequestAdvice {
    @ResponseBody
    @ExceptionHandler(BadRequestError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrorData serviceError(BadRequestError ex) {
        return new ApiErrorData(400,ex.getMessage());
    }
}
