package org.weather.errors;

public class ApiError extends Exception {
    public ApiError(String msg) {
        super(msg);
    }
}
