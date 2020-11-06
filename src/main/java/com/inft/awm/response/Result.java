package com.inft.awm.response;

/**
 * The class to return data as a uniform format
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/09/30 22:47 pm
 */
public class Result<T> {

    private String message = "Success";

    //200:Success   1:Fail
    private int code = 200;

    private T data;

    public Result(T body) {
        data = body;
    }

    public Result(int code,String message) {
        this.message = message;
        this.code = code;

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}