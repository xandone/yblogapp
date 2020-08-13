package com.app.xandone.yblogapp.model.base;

/**
 * author: Admin
 * created on: 2020/8/13 15:49
 * description:
 */
public class BaseResponse<T> {
    private int code;
    private T data;
    private String msg;
    private int total;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
