package com.app.xandone.yblogapp.ui.manager;

import android.animation.ObjectAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

import com.app.xandone.baselib.utils.MD5Util;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.widgetlib.utils.AnimUtils;
import com.app.xandone.widgetlib.utils.KeyboardWatcher;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.config.AppConfig;
import com.app.xandone.yblogapp.model.ManagerModel;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/8 11:53
 * description:
 */
public class ManagerFragment extends BaseWallFragment implements KeyboardWatcher.SoftKeyboardStateListener {
    @BindView(R.id.lock_iv)
    AppCompatImageView lockIv;
    @BindView(R.id.et_body_cl)
    ConstraintLayout etBodyCl;
    @BindView(R.id.login_account_et)
    EditText loginAccountEt;
    @BindView(R.id.login_psw_et)
    EditText loginPswEt;
    private KeyboardWatcher keyboardWatcher;

    private ManagerModel managerModel;

    @Override
    public int getLayout() {
        return R.layout.frag_manager;
    }

    @Override
    public void init(View view) {
        onLoadFinish();
        keyboardWatcher = new KeyboardWatcher(mActivity.findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);
    }

    @Override
    protected void initDataObserver() {
        managerModel = ModelProvider.getModel(mActivity, ManagerModel.class, App.sContext);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.login_btn})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        String name = loginAccountEt.getText().toString();
        String psw = loginPswEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入账户");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            ToastUtils.showShort("请输入密码");
            return;
        }
        psw = MD5Util.MD5(psw);
        managerModel.login(name, psw, new IRequestCallback<AdminBean>() {
            @Override
            public void success(AdminBean adminBean) {
                ToastUtils.showShort("登录成功");
            }

            @Override
            public void error(String message, int statusCode) {
                ToastUtils.showShort("登录异常" + message);
            }
        });
    }


    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        //获取body在屏幕中的坐标,控件左上角
        etBodyCl.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int bottom = AppConfig.SCREEN_HEIGHT - (y + etBodyCl.getHeight());
        if (keyboardSize > bottom) {
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(etBodyCl, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            AnimUtils.zoomIn(lockIv, keyboardSize - bottom, 0.8f);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(etBodyCl, "translationY", etBodyCl.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        AnimUtils.zoomOut(lockIv, 0.8f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
    }
}
