package com.app.xandone.baselib.base;

import androidx.annotation.LayoutRes;

/**
 * author: Admin
 * created on: 2020/8/12 10:56
 * description:
 */
public interface BaseInit {

    /**
     * 加载布局前的一些操作
     */
    void doBeforeSetContentView();

    @LayoutRes
    int getLayout();

    void init();
}
