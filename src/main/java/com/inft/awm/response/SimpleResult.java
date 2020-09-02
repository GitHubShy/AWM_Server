package com.inft.awm.response;

public class SimpleResult {
    private String message;

    public SimpleResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
