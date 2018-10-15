package org.weather.errors;

public class CityNotFoundError extends ApiError {

    public CityNotFoundError(String msg) {
        super(msg);
    }
}
