package com.stankin.lab6.responses;

public class Response {
    protected String message;

    public Response () {
    }

    public String getMessage () {
        return message;
    }

    public Response (String message) {
        this.message = message;
    }
}
