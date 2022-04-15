package com.app.xandone.baselib.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.baselib.R;

/**
 * author: Admin
 * created on: 2021/1/29 14:26
 * description:
 */
public class ProgressDialogHelper {

    private MaterialDialog dialog;

    private ProgressDialogHelper() {
    }

    public static ProgressDialogHelper getInstance() {
        return Builder.instance;
    }

    public void showLoading(Context context) {
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
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    private static class Builder {
        private static ProgressDialogHelper instance = new ProgressDialogHelper();
    }
}
