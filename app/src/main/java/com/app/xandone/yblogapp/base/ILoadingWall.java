package com.app.xandone.yblogapp.base;

/**
 * author: Admin
 * created on: 2020/9/1 11:19
 * description:
 */
public interface ILoadingWall {
    void onLoading();

    void onLoadEmpty();

    void onLoadSeverError();

    void onLoadNetError();

    void onLoadFinish();

    void onLoadStatus(int statusCode);
}
