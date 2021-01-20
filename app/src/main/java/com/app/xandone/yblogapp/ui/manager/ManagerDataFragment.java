package com.app.xandone.yblogapp.ui.manager;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.widgetlib.view.UserCircleView;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.ui.manager.admin.AdminListActivity;
import com.app.xandone.yblogapp.ui.manager.chart.ChartDataActivity;
import com.app.xandone.yblogapp.ui.manager.setting.SettingActivity;

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
    @BindView(R.id.admin_icon_uv)
    UserCircleView adminIconUv;
    @BindView(R.id.admin_name_tv)
    TextView adminNameTv;
    @BindView(R.id.admin_last_login_tv)
    TextView adminLastLoginTv;
    @BindView(R.id.admin_identity_tv)
    TextView adminIdentityTv;

    @Override
    public int getLayout() {
        return R.layout.frag_manager_data;
    }

    @Override
    public void init(View view) {
        setToolBar("管理系统");
        onLoadFinish();

        AdminBean adminBean = UserInfoHelper.getAdminBean();
        if (adminBean != null) {
            ImageLoadHelper.getInstance().display(mActivity, adminBean.getAdminIcon(), adminIconUv);
            adminNameTv.setText(adminBean.getNickname());
            adminIdentityTv.setText(adminBean.getIdentity());
            adminLastLoginTv.setText("最近登录:" + adminBean.getLastLoginTime());
        }
    }

    @Override
    protected void requestData() {

    }

    private void setToolBar(CharSequence title) {
        toolbar.setTitle(title);
    }

    @OnClick({R.id.setting_cl, R.id.chart_tip_cl, R.id.admin_tip_cl})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.chart_tip_cl:
                startActivity(new Intent(mActivity, ChartDataActivity.class));
                break;
            case R.id.setting_cl:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.admin_tip_cl:
                startActivity(new Intent(mActivity, AdminListActivity.class));
                break;
            default:
                break;
        }
    }
}
