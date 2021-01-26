package com.app.xandone.baselib.update;

import android.content.Context;

import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.MDialogUtils;

/**
 * author: Admin
 * created on: 2021/1/26 10:12
 * description:
 */
public class UpdateAgent implements IUpdateAgent {

    @Override
    public void showDialog(Context context, UpdateInfo updateInfo) {

        MDialogUtils.showVersionDialog(context, updateInfo, new MDialogOnclickListener() {
            @Override
            public void onConfirm() {

            }
        });
    }


}
