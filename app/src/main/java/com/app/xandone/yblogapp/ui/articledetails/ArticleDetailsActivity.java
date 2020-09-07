package com.app.xandone.yblogapp.ui.articledetails;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.widgetlib.utils.SizeUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.config.AppConfig;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.model.CodeDetailsModel;
import com.app.xandone.yblogapp.model.EssayDetailsModel;
import com.app.xandone.yblogapp.model.IArtDetailsModel;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/9/4 10:20
 * description:
 */
public class ArticleDetailsActivity extends BaseWallActivity {
    @BindView(R.id.webView)
    WebView webView;
    private IArtDetailsModel detailsModel;

    private String mId;
    private int mType;

    public static final int TYPE_CODE = 1;
    public static final int TYPE_ESSAY = 2;

    @Override
    public int getLayout() {
        return R.layout.act_article_details;
    }

    @Override
    public void init() {
        super.init();
        mId = getIntent().getStringExtra(IConstantKey.ID);
        mType = getIntent().getIntExtra(IConstantKey.TYPE, TYPE_CODE);
        initWebView();
    }

    @Override
    protected void initDataObserver() {
        if (mType == TYPE_CODE) {
            detailsModel = ModelProvider.getModel(this, CodeDetailsModel.class, App.sContext);
        } else {
            detailsModel = ModelProvider.getModel(this, EssayDetailsModel.class, App.sContext);
        }

        requestData();
    }

    @Override
    protected void requestData() {
        if (mType == TYPE_CODE) {
            detailsModel.getDetails(mId, new IRequestCallback<CodeDetailsBean>() {
                @Override
                public void success(CodeDetailsBean codeDetailsBean) {
                    String html = codeDetailsBean.getContentHtml().replace("<pre", "<pre style=\"overflow: auto;background-color: #F3F5F8;padding:10px;\"");
                    LogHelper.d(html);
                    webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                    onLoadFinish();
                }

                @Override
                public void error(String message, int statusCode) {
                    onLoadStatus(statusCode);
                }
            });
        } else {
            detailsModel.getDetails(mId, new IRequestCallback<EssayDetailsBean>() {
                @Override
                public void success(EssayDetailsBean essayDetailsBean) {
                    String html = essayDetailsBean.getContentHtml().replace("<pre", "<pre style=\"overflow: auto;background-color: #F3F5F8;padding:10px;\"");
                    LogHelper.d(html);
                    webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                    onLoadFinish();
                }

                @Override
                public void error(String message, int statusCode) {
                    onLoadStatus(statusCode);
                }
            });
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings ws = webView.getSettings();
//        // 网页内容的宽度是否可大于WebView控件的宽度
//        ws.setLoadWithOverviewMode(false);
//        // 是否应该支持使用其屏幕缩放控件和手势缩放
//        ws.setSupportZoom(true);
//        ws.setBuiltInZoomControls(true);
//        ws.setDisplayZoomControls(false);
//        // 设置此属性，可任意比例缩放。
//        ws.setUseWideViewPort(false);
//        //  页面加载好以后，再放开图片
//        ws.setBlockNetworkImage(false);
//        // 排版适应屏幕
//        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setJavaScriptEnabled(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //修改图片大小
        int screenWidth = AppConfig.SCREEN_WIDTH;
        String width = String.valueOf(SizeUtils.px2dp(App.sContext, screenWidth) - 20);
//        int fonsSize = SizeUtils.sp2px(App.sContext, 14);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                String javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > " + width + "){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width =" + width + ";" +
                        "}" +
                        "}" +
                        "}";
                view.loadUrl(javascript);
                view.loadUrl("javascript:ResizeImages();");

                view.loadUrl("javascript:function modifyTextColor(){" +
                        "document.getElementsByTagName('body')[0].style.webkitTextFillColor='#333';" +
                        "document.getElementsByTagName('body')[0].style.background='white';" +
                        "};modifyTextColor();");

            }
        });
    }

}
