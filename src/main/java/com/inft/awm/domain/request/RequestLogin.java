package com.inft.awm.domain.request;
/**
 * A class used for saving parameters for a api interface to request deleting a employee
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 13:12 pm
 */
public class RequestLogin {
    private String account_name;
    private String password;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
