package com.app.xandone.widgetlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.xandone.widgetlib.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * @author: Admin
 * created on: 2022/5/13 14:34
 * description:
 */
public class SettingView extends FrameLayout {
    private TextView settingTv;
    private TextView settingRightTv;
    private AppCompatImageView settingArrowIv;

    public SettingView(@NonNull Context context) {
        this(context, null);
    }

    public SettingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.xwidget_v_setting_layout, this);

        settingTv = view.findViewById(R.id.setting_tv);
        settingArrowIv = view.findViewById(R.id.setting_arrow_iv);
        settingRightTv = view.findViewById(R.id.setting_right_tv);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.xwidget_SettingView);
            String tvStr = array.getString(R.styleable.xwidget_SettingView_xwidget_text);
            String tvRightStr = array.getString(R.styleable.xwidget_SettingView_xwidget_right_text);
            int tvColor = array.getColor(R.styleable.xwidget_SettingView_xwidget_text_color, ContextCompat.getColor(context, R.color.xwidget_text_default));
            int tvRightColor = array.getColor(R.styleable.xwidget_SettingView_xwidget_right_text_color, ContextCompat.getColor(context, R.color.xwidget_text_default));
            Drawable leftIc = array.getDrawable(R.styleable.xwidget_SettingView_xwidget_left_ic);

            if (!TextUtils.isEmpty(tvStr)) {
                settingTv.setText(tvStr);
            }
            settingTv.setTextColor(tvColor);
            if (leftIc != null) {
                leftIc.setBounds(0, 0, leftIc.getIntrinsicWidth(), leftIc.getIntrinsicHeight());
                settingTv.setCompoundDrawables(leftIc, null, null, null);
            }

            settingRightTv.setText(tvRightStr);
            settingRightTv.setTextColor(tvRightColor);

            array.recycle();
        }
    }

    public void setSettingRightTv(CharSequence sequence) {
        if (settingRightTv != null) {
            settingRightTv.setText(sequence);
        }
    }
}
