package com.app.xandone.yblogapp.ui.manager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.widgetlib.view.UserCircleView;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.ActManager;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.ui.manager.admin.AdminListActivity;
import com.app.xandone.yblogapp.ui.manager.chart.ChartDataActivity;
import com.app.xandone.yblogapp.ui.manager.setting.SettingActivity;

import java.text.Format;
import java.util.Collection;
import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/27 11:02
 * description:
 */
public class ManagerDataFragment extends BaseWallFragment {
    @BindView(R.id.admin_icon_uv)
    UserCircleView adminIconUv;
    @BindView(R.id.admin_name_tv)
    TextView adminNameTv;
    @BindView(R.id.admin_last_login_tv)
    TextView adminLastLoginTv;
    @BindView(R.id.admin_identity_tv)
    TextView adminIdentityTv;
    @BindView(R.id.code_tip_cl)
    ConstraintLayout code_tip_cl;

    @Override
    public int getLayout() {
        return R.layout.frag_manager_data;
    }

    @Override
    public void init(View view) {
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

    @OnClick({R.id.setting_cl, R.id.chart_tip_cl, R.id.admin_tip_cl, R.id.code_tip_cl})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.chart_tip_cl:
                startActivity(new Intent(mActivity, ChartDataActivity.class));
                break;
            case R.id.code_tip_cl:

                code_tip_cl.setSelected(!code_tip_cl.isSelected());

                if (code_tip_cl.isSelected()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                code_tip_cl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Collection<BaseSimpleActivity> list = ActManager.getInstance().getAllActivity().values();
                        for (BaseSimpleActivity act : list) {
                            act.recreate();
                        }
                    }
                }, 300);

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
