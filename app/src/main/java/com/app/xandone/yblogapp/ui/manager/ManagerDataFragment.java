package com.app.xandone.yblogapp.ui.manager;

import android.content.Intent;
import android.view.View;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.widgetlib.dialog.MDialogOnclickListener;
import com.app.xandone.widgetlib.dialog.MDialogUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.ui.manager.setting.SettingActivity;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/27 11:02
 * description:
 */
public class ManagerDataFragment extends BaseWallFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.frag_manager_data;
    }

    @Override
    public void init(View view) {
        setToolBar("管理系统");
        onLoadFinish();
    }

    @Override
    protected void requestData() {

    }

    private void setToolBar(CharSequence title) {
        toolbar.setTitle(title);
    }

    @OnClick({R.id.setting_cl, R.id.exit_btn})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.setting_cl:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.exit_btn:
                exit();
                break;
            default:
                break;
        }
    }

    private void exit() {
        MDialogUtils.showSimpleDialog(mActivity, "是否退出登录？", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                EventBus.getDefault().post(new SwitchEvent(SwitchEvent.MANAGER_LOGIN_RAG));
                CacheHelper.clearDefaultSp(App.sContext, ISpKey.ADMIN_INFO_KEY);
            }
        });

    }
}
