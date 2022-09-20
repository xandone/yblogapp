package com.app.xandone.baselib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import com.app.xandone.baselib.config.BaseConfig;
import com.blankj.utilcode.util.Utils;

import androidx.annotation.NonNull;

/**
 * 复制到剪贴板
 */
public class CopyUtils {

    public static void copyText(@NonNull String text) {
        ClipboardManager cm = (ClipboardManager) BaseConfig.sApp.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(BaseConfig.sApp.getPackageName(), text));
    }

    public static void copyText(@NonNull TextView textView) {
        copyText(textView.getText().toString());
    }
}
