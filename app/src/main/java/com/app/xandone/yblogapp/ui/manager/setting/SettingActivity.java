package com.app.xandone.yblogapp.ui.manager.setting;

import android.view.View;
import android.widget.TextView;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.widgetlib.dialog.MDialogOnclickListener;
import com.app.xandone.widgetlib.dialog.MDialogUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.constant.ISpKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/29 11:09
 * description:
 */
public class SettingActivity extends BaseWallActivity {
    @BindView(R.id.all_cache_size_tv)
    TextView allCacheSizeTv;

    @Override
    public int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    public void wallInit() {
        try {
            allCacheSizeTv.setText(CacheHelper.getTotalCacheSize(App.sContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                clearAllCache();
                break;
            default:
                break;
        }
    }

    private void clearSettingInfo() {
        MDialogUtils.showSimpleDialog(this, "是否清除配置信息?", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                CacheHelper.clearDefaultSp(App.sContext, ISpKey.CODE_TYPE_KEY);
                ToastUtils.showShort("清除完成");
            }
        });

    }

    private void clearAllCache() {
        MDialogUtils.showSimpleDialog(this, "是否清除所有缓存文件?", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                CacheHelper.clearExternalFilesDir(App.sContext);
                allCacheSizeTv.setText("0KB");
                ToastUtils.showShort("清除完成");
            }
        });
    }
}
