package com.app.xandone.yblogapp.ui.manager.setting;

import android.view.View;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.constant.ISpKey;

import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/29 11:09
 * description:
 */
public class SettingActivity extends BaseWallActivity {
    @Override
    public int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    public void wallInit() {
        onLoadFinish();
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.clear_setting_cl, R.id.clear_all_cache_cl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.clear_setting_cl:
                clearSettingInfo();
                break;
            case R.id.clear_all_cache_cl:
                break;
            default:
                break;
        }
    }

    private void clearSettingInfo() {
        CacheHelper.clearDefaultSp(App.sContext, ISpKey.CODE_TYPE_KEY);
    }
}
