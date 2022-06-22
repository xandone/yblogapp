package com.app.xandone.baselib.base;

import android.view.View;

import androidx.annotation.LayoutRes;

/**
 * author: Admin
 * created on: 2020/9/1 10:32
 * description:
 */
public interface IFragInit {
    /**
     * 加载布局前的一些操作
     */
    void doBeforeSetContentView();

    View getLayout();

    void init(View view);
}
