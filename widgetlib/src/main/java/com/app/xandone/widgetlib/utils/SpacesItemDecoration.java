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
    private int itemSpaceH;
    private int itemSpaceHLeft;
    private int itemSpaceV;

    /**
     * @param itemSpaceH 横向
     * @param itemSpaceV 纵向
     */
    public SpacesItemDecoration(Context context, int itemSpaceH, int itemSpaceV) {
        this.itemSpaceH = SizeUtils.dp2px(context, itemSpaceH);
        this.itemSpaceV = SizeUtils.dp2px(context, itemSpaceV);
    }

    public SpacesItemDecoration(Context context, int itemSpaceHL, int itemSpaceHR, int itemSpaceV) {
        this.itemSpaceH = SizeUtils.dp2px(context, itemSpaceHR);
        this.itemSpaceHLeft = SizeUtils.dp2px(context, itemSpaceHL);
        this.itemSpaceV = SizeUtils.dp2px(context, itemSpaceV);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = itemSpaceV;

        outRect.right = itemSpaceH;

        if (itemSpaceHLeft > 0) {
            outRect.left = itemSpaceHLeft;
        }


    }
}