package com.app.xandone.yblogapp.ui.manager;

import android.view.View;

import com.app.xandone.baselib.utils.KeyBoardUtils;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.databinding.FragManagerBinding;
import com.app.xandone.yblogapp.databinding.FragManagerDataBinding;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * author: Admin
 * created on: 2020/9/8 11:53
 * description:
 */
public class ManagerFragment extends BaseWallFragment<FragManagerBinding> {

    @Override
    public View getLayout() {
        mBinding = FragManagerBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void init(View view) {
        autoCheckAdminInfo();
        onLoadFinish();
    }

    @Override
    protected void requestData() {

    }

    /**
     * 自动检测缓存的Admin信息
     */
    private void autoCheckAdminInfo() {
        AdminBean adminBean = UserInfoHelper.getAdminBean();
        if (adminBean == null) {
            showInitView();
            return;
        }
        switchFragment(new ManagerDataFragment());
    }


    private void showInitView() {
        getChildFragmentManager().beginTransaction().replace(R.id.main_fl, new ManagerLoginFragment()).commit();
    }

    private void switchFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                .replace(R.id.main_fl, fragment)
                .commitAllowingStateLoss();
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchEvent(SwitchEvent event) {
        if (isDetached()) {
            return;
        }
        KeyBoardUtils.hideKeyboard(mActivity.getCurrentFocus());
        Fragment fragment;
        switch (event.getTag()) {
            case SwitchEvent.MANAGER_DATA_RAG:
                fragment = new ManagerDataFragment();
                break;
            default:
                fragment = new ManagerLoginFragment();
                break;
        }

        switchFragment(fragment);
    }

    @Override
    protected boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected View getTitleView() {
        return mBinding.mainFl;
    }
}
