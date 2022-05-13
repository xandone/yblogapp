package com.app.xandone.baselib.base;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: Admin
 * created on: 2022/5/13 16:12
 * description:
 */
public interface IClickAction extends View.OnClickListener {

    <T extends View> T findView(@IdRes int id);

    default void bindClick(@IdRes int... viewIds) {
        bindClick(this, viewIds);
    }

    default void bindClick(View.OnClickListener clickListener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            findView(id).setOnClickListener(clickListener);
        }
    }

    @Override
    default void onClick(View v) {

    }
}
