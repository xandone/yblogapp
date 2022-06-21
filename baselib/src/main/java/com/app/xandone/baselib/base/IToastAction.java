package com.app.xandone.baselib.base;


import com.app.xandone.baselib.utils.ToastHelper;

import androidx.annotation.StringRes;

/**
 * @author: Admin
 * created on: 2022/4/15 14:55
 * description:
 */
public interface IToastAction {

    default void toast(CharSequence msg) {
        ToastHelper.showShort(msg);
    }

    default void toast(@StringRes int strId) {
        ToastHelper.showShort(strId);
    }
}
