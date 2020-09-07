package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.rx.IRequestCallback;

/**
 * author: Admin
 * created on: 2020/9/7 10:10
 * description:
 */
public interface IArtDetailsModel<T> {
    void getDetails(String id, IRequestCallback<T> callback);
}
