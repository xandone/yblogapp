package com.app.xandone.baselib.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * author: Admin
 * created on: 2021/4/22 09:54
 * description:
 */
public class KeyBoardUtils {
    /**
     * 显示软键盘
     */
    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 切换软键盘
     */
    public static void toggleSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.toggleSoftInput(0, 0);
        }
    }

}
