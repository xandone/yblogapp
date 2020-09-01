package com.app.xandone.yblogapp.base;

/**
 * author: Admin
 * created on: 2020/9/1 11:19
 * description:
 */
public interface ILoadingWall {
    void loading();

    void loadEmpty();

    void loadSeverError();

    void loadNetError();

    void loadFinish();
}
