package com.app.xandone.yblogapp.ui.manager;

import android.view.View;

import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;

/**
 * author: Admin
 * created on: 2020/9/8 11:53
 * description:
 */
public class ManagerFragment extends BaseWallFragment {

    @Override
    public int getLayout() {
        return R.layout.frag_manager;
    }

    @Override
    public void init(View view) {
        onLoadFinish();
    }

    @Override
    protected void requestData() {

    }
}
