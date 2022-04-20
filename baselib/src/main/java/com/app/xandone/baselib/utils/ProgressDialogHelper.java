package com.app.xandone.baselib.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.baselib.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * author: Admin
 * created on: 2021/1/29 14:26
 * description:
 */
public class ProgressDialogHelper {


    private LoadingPopupView loadingPopup;

    /**
     * @deprecated
     */
    private MaterialDialog dialog;

    private ProgressDialogHelper() {
    }

    public static ProgressDialogHelper getInstance() {
        return Builder.INSTANCE;
    }

    public void showLoading(Context context) {
        if (loadingPopup != null && loadingPopup.dialog != null && loadingPopup.dialog.isShowing()) {
            return;
        }
        loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .isLightNavigationBar(true)
                .isViewMode(false)
                .asLoading("加载中")
                .show();
    }

    /**
     * @param context
     * @deprecated
     */
    public void showLoading2(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(R.string.progress_tip)
                .titleColorRes(R.color.white)
                .contentColor(Color.WHITE)
                .backgroundColorRes(R.color.alpha_black_80)
                .progressIndeterminateStyle(true)
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();

        if (!dialog.isShowing()) {
            dialog.show();
            if (dialog.getWindow() != null) {
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = (int) (AppUtils.getScreenWidth(context) * 0.4);
                dialog.getWindow().setAttributes(lp);
            }
        }

    }

    public void dimissLoading() {
        if (loadingPopup != null) {
            loadingPopup.dismiss();
        }
    }


    private static class Builder {
        private static final ProgressDialogHelper INSTANCE = new ProgressDialogHelper();
    }
}
