package org.weather.errors;

public class ApiErrorData {
    public int code;
    public String msg;

    public ApiErrorData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
