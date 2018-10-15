package org.weather.errors;

public class BadRequestError extends ApiError {
    public BadRequestError(String msg) {
        super(msg);
    }
}
