package com.inft.awm.response;
/**
 * The class to return data as a uniform format
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/09/30 21:47 pm
 */
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
