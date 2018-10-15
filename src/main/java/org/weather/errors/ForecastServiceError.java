package org.weather.errors;

public class ForecastServiceError extends Exception {
    // Status code returned by Open Weather Map API
    public int statusCode;

    public ForecastServiceError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
