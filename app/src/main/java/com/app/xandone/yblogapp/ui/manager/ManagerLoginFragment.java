package com.app.xandone.yblogapp.ui.manager;

import android.animation.ObjectAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

import com.app.xandone.baselib.base.BaseFrament;
import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.baselib.utils.MD5Util;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.widgetlib.utils.AnimUtils;
import com.app.xandone.widgetlib.utils.KeyboardWatcher;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.config.AppConfig;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.ManagerModel;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/29 09:52
 * description:
 */
public class ManagerLoginFragment extends BaseFrament implements KeyboardWatcher.SoftKeyboardStateListener {
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
        return R.layout.frag_manager_login;
    }

    @Override
    public void init(View view) {
        keyboardWatcher = new KeyboardWatcher(mActivity.findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);
    }

    @Override
    protected void initDataObserver() {
        managerModel = ModelProvider.getModel(mActivity, ManagerModel.class, App.sContext);
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
            toast("请输入账户");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            toast("请输入密码");
            return;
        }
        psw = MD5Util.MD5(psw);
        managerModel.login(name, psw, new IRequestCallback<AdminBean>() {
            @Override
            public void success(AdminBean adminBean) {
                EventBus.getDefault().post(new SwitchEvent(SwitchEvent.MANAGER_DATA_RAG));
                savaLoginInfo(adminBean);
                toast("登录成功");
            }

            @Override
            public void error(String message, int statusCode) {
                toast("登录异常" + message);
            }
        });
    }

    /**
     * 缓存登录信息
     *
     * @param adminBean
     */
    private void savaLoginInfo(AdminBean adminBean) {
        SpHelper.save2DefaultSp(App.sContext, ISpKey.ADMIN_INFO_KEY, JsonUtils.obj2Json(adminBean));
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
