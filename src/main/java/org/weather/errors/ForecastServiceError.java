package org.weather.errors;

public class ForecastServiceError extends Exception {
    public ForecastServiceError(String message) {
        super(message);
    }
}
