package com.app.xandone.yblogapp.base;

/**
 * author: Admin
 * created on: 2020/9/2 11:31
 * description:
 */
public interface IRefreshCallback {
    void getData();

    void getDataMore();

    void finishRefresh();

    void finishLoadMore();

    void finishLoadNoMoreData();
}
