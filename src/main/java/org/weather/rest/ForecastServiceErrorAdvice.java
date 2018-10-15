package org.weather.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.weather.errors.ForecastServiceError;

@ControllerAdvice
public class ForecastServiceErrorAdvice {
    @ResponseBody
    @ExceptionHandler(ForecastServiceError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String serviceError(ForecastServiceError ex) {
        return ex.getMessage();
    }
}
