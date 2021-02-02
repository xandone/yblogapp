package com.app.xandone.baselib.dialog;

import android.content.Context;
import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.baselib.R;
import com.app.xandone.baselib.update.UpdateInfo;

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

    public static void showVersionDialog(Context context, UpdateInfo updateInfo, final MDialogOnclickListener listener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        if (!updateInfo.isForce()) {
            builder.negativeText(R.string.cancle);
            builder.negativeColorRes(R.color.btn_color);
            builder.neutralText(R.string.ignore_version);
            builder.neutralColorRes(R.color.btn_color);
        }
        builder.title(R.string.dialog_title)
                .positiveText(R.string.download)
                .positiveColorRes(R.color.btn_color)
                .content(updateInfo.getVersionTip())
                .cancelable(!updateInfo.isForce())
                .canceledOnTouchOutside(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        switch (which) {
                            case NEGATIVE:
                                listener.onCancle();
                                break;
                            case POSITIVE:
                                listener.onConfirm();
                                break;
                            case NEUTRAL:
                                listener.onNeutral();
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static void showProgressDialog(Context context) {
        new MaterialDialog.Builder(context)
                .content(R.string.progress_tip)
                .titleColorRes(R.color.white)
                .contentColor(Color.WHITE)
                .backgroundColorRes(R.color.alpha_black_80)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
    }


    public static void showDownloadProgressDialog(Context context) {
        new MaterialDialog.Builder(context)
                .content(R.string.progress_tip)
                .titleColorRes(R.color.white)
                .contentColor(Color.WHITE)
                .backgroundColorRes(R.color.alpha_black_80)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
    }
}
