package com.sctuopuyi.echo.data.http.response;

/**
 * Created on 02/03/2017 16:04.
 */

public class BaseHttpResponse<T> {

    public BaseHttpResponse() {
    }

    public BaseHttpResponse(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    private String code;
    private String msg;
    private T result;

    @Override
    public String toString() {
        return "BaseHttpResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
