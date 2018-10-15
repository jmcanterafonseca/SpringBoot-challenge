package org.weather.errors;

import sun.nio.ch.Net;

public class NetworkError extends ApiError {
    public NetworkError(String msg) {
        super(msg);
    }
}
