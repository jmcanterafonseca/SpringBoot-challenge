package org.weather.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.weather.errors.CityNotFoundError;
import org.weather.errors.ForecastServiceError;

@ControllerAdvice
public class CityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CityNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String serviceError(ForecastServiceError ex) {
        return ex.getMessage();
    }
}
