package org.weather.errors;

public class CityNotFoundError extends Exception {

    public CityNotFoundError(String msg) {
        super(msg);
    }
}
