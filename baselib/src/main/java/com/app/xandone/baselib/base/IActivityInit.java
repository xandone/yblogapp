package com.app.xandone.baselib.base;

import android.view.View;


/**
 * author: Admin
 * created on: 2020/8/12 10:56
 * description:
 */
public interface IActivityInit {

    /**
     * 加载布局前的一些操作
     */
    void doBeforeSetContentView();

    View getLayout();

    default void initView() {
    }

    void init();
}
