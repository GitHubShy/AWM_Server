package com.inft.awm.response;

/**
 * Response class for login request
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/10/15 15:00 pm
 */
public class ResponseLogin {

    private String token;

    public ResponseLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
