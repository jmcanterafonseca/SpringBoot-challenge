package org.weather.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.weather.errors.ApiErrorData;
import org.weather.errors.ForecastServiceError;
import org.weather.errors.NetworkError;

@ControllerAdvice
public class NetworkErrorAdvice {
    @ResponseBody
    @ExceptionHandler(NetworkError.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    ApiErrorData serviceError(NetworkError ex) {
        return new ApiErrorData(HttpStatus.SERVICE_UNAVAILABLE.value(),ex.getMessage());
    }
}
