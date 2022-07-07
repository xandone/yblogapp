package com.app.xandone.yblogapp.base;

/**
 * author: Admin
 * created on: 2020/9/2 11:31
 * description:
 */
public interface IRefreshCallback {
    /**
     * 获取第一页接口数据
     */
    void getApiData();

    /**
     * 获取第N页接口数据
     */
    void getApiDataMore();

    /**
     * 下拉结束
     */
    void finishRefresh();

    /**
     * 不允许下拉
     */
    void unableRefresh();

    /**
     * 上拉结束
     */
    void finishLoadMore();

    /**
     * 上拉结束，且没有数据
     */
    void finishLoadNoMoreData();

    /**
     * 不允许上拉
     */
    void unableLoadMore();

}
