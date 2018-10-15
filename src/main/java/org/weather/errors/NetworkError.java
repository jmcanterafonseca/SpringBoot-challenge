package org.weather.errors;

import sun.nio.ch.Net;

public class NetworkError extends Exception {
    public NetworkError(String msg) {
        super(msg);
    }
}
