package com.app.xandone.widgetlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * author: Admin
 * created on: 2020/9/4 09:27
 * description:
 */
public class CodeEditTextView extends AppCompatEditText implements View.OnClickListener {
    private float paddingHorizontal;
    private float lineSpace;
    private float lineWidth;
    private RectF rectF = new RectF();
    private Rect bounds = new Rect();
    private int mLineColor;
    private int mLineColorFilled;
    private int mBgColor = Color.TRANSPARENT;

    private Paint paint;

    private static final int MAX_LENTH = 4;

    public CodeEditTextView(Context context) {
        super(context);
        init(context);
    }

    public CodeEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        lineWidth = dp2px(context, 2);
        mLineColor = Color.BLACK;
        mLineColorFilled = Color.BLACK;
        setCursorVisible(false);
        setOnClickListener(this);
        setBackgroundResource(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0, 0, w, h);
        lineSpace = w * 0.2f;
        paddingHorizontal = lineSpace / (MAX_LENTH + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(mBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, getHeight() / 2, getHeight() / 2, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setTextSize(getTextSize());
        paint.setColor(getTextColors().getDefaultColor());

        char[] chars;
        int len = 0;
        if (getText() != null) {
            chars = getText().toString().toCharArray();
            len = chars.length;
            for (int i = 0; i < len; i++) {
                paint.getTextBounds(chars, i, 1, bounds);
                canvas.drawText(chars, i, 1, lineSpace / 2 + (paddingHorizontal + lineSpace) * i + paddingHorizontal / 2, getHeight() / 2 + bounds.height() / 2, paint);
            }
        }

        paint.setColor(mLineColor);
        paint.setStrokeWidth(lineWidth);
        for (int i = 0; i < MAX_LENTH; i++) {
            if (i == 0 || i < len) {
                paint.setColor(mLineColorFilled);
            } else {
                paint.setColor(mLineColor);
            }
            canvas.drawLine((paddingHorizontal + lineSpace) * i + paddingHorizontal, getHeight(), (i + 1) * (lineSpace + paddingHorizontal), getHeight(), paint);
        }

    }

    @Override
    public void onClick(View v) {
        int index = (getText() == null) ? 0 : getText().length();
        setSelection(index);
    }

    public int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}