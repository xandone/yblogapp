package com.app.xandone.widgetlib.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.xandone.widgetlib.R;


/**
 * author: Admin
 * created on: 2020/8/13 16:29
 * description:
 */
public class LoadingLayout extends LinearLayout {
    private ImageView imgTipLogo;
    private ProgressBar progressBar;
    private TextView tvTips;
    private TextView btOperate;

    private OnReloadListener onReloadListener;
    private String errorMsg;
    private int nullPic;

    private Context mContext;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    public void initView(Context context) {
        View.inflate(context, R.layout.xwidget_loading_tip_layout, this);
        imgTipLogo = findViewById(R.id.img_tip_logo);
        progressBar = findViewById(R.id.progress);
        tvTips = findViewById(R.id.tv_tips);
        btOperate = findViewById(R.id.bt_operate);

        setVisibility(GONE);

        btOperate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadListener != null) {
                    onReloadListener.reLoadData();
                }
            }
        });

    }

    public void setTips(String tips) {
        if (tvTips == null) {
            return;
        }
        tvTips.setText(tips);
    }

    public int getNullPic() {
        return nullPic;
    }

    public void setNullPic(int nullPic) {
        this.nullPic = nullPic;
    }

    public void setLoadingStatus(int loadStatus) {
        switch (loadStatus) {
            case ILoadingStatus.NET_ERROR:
                setVisibility(VISIBLE);
                imgTipLogo.setVisibility(VISIBLE);
                stopProgress(mContext);
                btOperate.setVisibility(VISIBLE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tvTips.setText("数据加载失败");
                } else {
                    tvTips.setText(errorMsg);
                }
                imgTipLogo.setImageResource(R.drawable.icon_net_error);
                break;
            case ILoadingStatus.SERVER_ERROR:
                setVisibility(View.VISIBLE);
                imgTipLogo.setVisibility(VISIBLE);
                stopProgress(mContext);
                btOperate.setVisibility(VISIBLE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tvTips.setText("服务器异常");
                } else {
                    tvTips.setText(errorMsg);
                }
                imgTipLogo.setImageResource(R.drawable.icon_server_error);
                break;
            case ILoadingStatus.EMPTY:
                setVisibility(VISIBLE);
                imgTipLogo.setVisibility(VISIBLE);
                stopProgress(mContext);
                btOperate.setVisibility(VISIBLE);
                tvTips.setText("暂无数据");
                if (nullPic <= 0) {
                    imgTipLogo.setImageResource(R.drawable.icon_net_nodata
                    );
                } else {
                    imgTipLogo.setImageResource(nullPic);
                }
                break;
            case ILoadingStatus.LOADING:
                setVisibility(VISIBLE);
                imgTipLogo.setVisibility(GONE);
                startProgress(mContext);
                btOperate.setVisibility(GONE);
                tvTips.setText("加载..");
                break;
            case ILoadingStatus.FINISH:
                setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    private void startProgress(Context context) {
        progressBar.setVisibility(VISIBLE);
//        progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context,
//                R.drawable.loading_progressbar));
//        progressBar.setProgressDrawable(ContextCompat.getDrawable(context,
//                R.drawable.loading_progressbar));
    }

    private void stopProgress(Context context) {
        progressBar.setVisibility(GONE);
//        progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context,
//                R.mipmap.loading_progress));
//        progressBar.setProgressDrawable(ContextCompat.getDrawable(context,
//                R.mipmap.loading_progress));
    }

    public interface OnReloadListener {
        void reLoadData();
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface ILoadingStatus {
        int NET_ERROR = 1;
        int SERVER_ERROR = 2;
        int EMPTY = 3;
        int LOADING = 4;
        int FINISH = 5;
    }

}
