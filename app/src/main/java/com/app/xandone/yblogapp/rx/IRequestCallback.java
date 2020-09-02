package com.app.xandone.yblogapp.rx;


/**
 * author: Admin
 * created on: 2020/9/2 10:07
 * description:
 */
public interface IRequestCallback<T> {
    void success(T t);

    void error(String message, int statusCode);
}
