package com.app.xandone.widgetlib.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.widgetlib.R;

import androidx.annotation.NonNull;

/**
 * author: Admin
 * created on: 2020/9/29 15:14
 * description:
 */
public class MDialogUtils {
    public static void showSimpleDialog(Context context, String content, final MDialogOnclickListener listener) {
        new MaterialDialog.Builder(context)
                .content(content)
                .title(R.string.dialog_title)
                .negativeText(R.string.cancle)
                .positiveText(R.string.confirm)
                .negativeColorRes(R.color.btn_color)
                .positiveColorRes(R.color.btn_color)
                .canceledOnTouchOutside(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            dialog.dismiss();
                            listener.onCancle();
                        }
                        if (which == DialogAction.POSITIVE) {
                            dialog.dismiss();
                            listener.onConfirm();
                        }
                    }
                })
                .show();
    }
}
