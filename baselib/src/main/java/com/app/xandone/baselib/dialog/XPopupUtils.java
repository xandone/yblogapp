package com.app.xandone.baselib.dialog;

import android.content.Context;

import com.app.xandone.baselib.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.SimpleCallback;

import androidx.core.content.ContextCompat;


/**
 * @author: xiao
 * created on: 2022/6/13 14:32
 * description:
 */
public class XPopupUtils {
    public static void showSimpleDialog(Context ctx, String content, final MDialogOnclickListener listener) {
        new XPopup.Builder(ctx)
                .isDestroyOnDismiss(true)
                .dismissOnTouchOutside(false)
                .dismissOnTouchOutside(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .setPopupCallback(new SimpleCallback() {

                    @Override
                    public void beforeShow(BasePopupView popupView) {
                        if (popupView instanceof ConfirmPopupView) {
                            ConfirmPopupView confirmPopupView = (ConfirmPopupView) popupView;
                            confirmPopupView.getConfirmTextView().setTextColor(ContextCompat.getColor(ctx, R.color.btn_color));
                        }
                    }
                })
                .asConfirm(ctx.getString(R.string.dialog_title),
                        content,
                        ctx.getString(R.string.cancle),
                        ctx.getString(R.string.confirm),
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                listener.onConfirm();
                            }
                        }, new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                listener.onCancle();
                            }
                        },
                        false)
                .show();
    }
}
