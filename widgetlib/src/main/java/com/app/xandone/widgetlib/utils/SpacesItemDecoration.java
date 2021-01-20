package com.app.xandone.widgetlib.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author: Admin
 * created on: 2018/10/9 14:59
 * description:
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int itemSpaceHLeft;
    private int itemSpaceRight;
    private int itemSpaceTop;
    private int itemSpaceBottom;

    /**
     * @param itemSpaceLeft 横向
     * @param itemSpaceTop  纵向
     */
    public SpacesItemDecoration(Context context, int itemSpaceLeft, int itemSpaceTop) {
        this.itemSpaceHLeft = SizeUtils.dp2px(context, itemSpaceLeft);
        this.itemSpaceTop = SizeUtils.dp2px(context, itemSpaceTop);
    }

    public SpacesItemDecoration(Context context, int itemSpaceHL, int itemSpaceHR, int itemSpaceV) {
        this.itemSpaceHLeft = SizeUtils.dp2px(context, itemSpaceHR);
        this.itemSpaceRight = SizeUtils.dp2px(context, itemSpaceHL);
        this.itemSpaceTop = SizeUtils.dp2px(context, itemSpaceV);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = itemSpaceTop;

        outRect.left = itemSpaceHLeft;

        if (itemSpaceRight > 0) {
            outRect.right = itemSpaceRight;
        }


    }
}