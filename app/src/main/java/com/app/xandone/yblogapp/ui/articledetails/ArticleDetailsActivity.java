package com.app.xandone.yblogapp.ui.articledetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.xandone.baselib.cache.ImageCache;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.ImageUtils;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.widgetlib.dialog.bottom.BottomDialog;
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
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.vansz.universalimageloader.UniversalImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

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
    private String mTitle;
    private Transferee transfer;
    private List<String> urls;

    private DownloadTask task;
    private DownloadListener mDownloadListener;

    private BottomDialog downloadDialog;

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
        mTitle = getIntent().getStringExtra(IConstantKey.TITLE);
        setToolBar(mTitle);
        transfer = Transferee.getDefault(this);
        urls = new ArrayList<>();
        initWebView();
        initDownloadListener();
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

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
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
        webView.addJavascriptInterface(this, "imgClick");

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

                view.loadUrl("javascript:function addImgClickEvent() {" +
                        "        var objs = document.getElementsByTagName(\"img\");" +
                        "        for (var i = 0; i < objs.length; i++) {" +
                        "            objs[i].index = i;" +
                        "            objs[i].onclick = function() {" +
                        "              imgClick.showImg(this.src,this.index);" +
                        "            }" +
                        "        }" +
                        "    }" +
                        "    addImgClickEvent();");

            }
        });
    }

    @SuppressLint("CheckResult")
    @JavascriptInterface
    public void showImg(String url, int position) {
        Observable.just(url)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                urls.clear();
                urls.add(s);
                transfer.apply(TransferConfig.build()
                        .setImageLoader(UniversalImageLoader.with(getApplicationContext()))
                        .setSourceUrlList(urls)
                        .setOnLongClickListener(new Transferee.OnTransfereeLongClickListener() {
                            @Override
                            public void onLongClick(ImageView imageView, String imageUri, int pos) {
                                showDownloadDialog(imageUri);
                            }
                        })
                        .create()
                ).show();
            }
        });

    }


    private void showDownloadDialog(String imageUri) {
        downloadDialog = BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.save_img_tv:
                                        downloadImg(imageUri);
                                        break;
                                    default:
                                }
                                downloadDialog.dismiss();
                            }
                        };

                        v.findViewById(R.id.save_img_tv).setOnClickListener(listener);
                        v.findViewById(R.id.cache_img_tv).setOnClickListener(listener);
                    }
                })
                .setLayoutRes(R.layout.dialog_download_img)
                .setDimAmount(0.6f)
                .setHeight(SizeUtils.dp2px(App.sContext, 200))
                .setTag("BottomDialog");
        downloadDialog.show();
    }

    private void downloadImg(String url) {
        task = new DownloadTask.Builder(url, new File(ImageCache.getImageCache(App.sContext)))
                .setFilename(System.currentTimeMillis() + ".jpg")
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();

        task.enqueue(mDownloadListener);

    }

    private void initDownloadListener() {
        mDownloadListener = new DownloadListener() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {
            }

            @Override
            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {
            }

            @Override
            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
            }

            @Override
            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {
            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
            }

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
            }

            @Override
            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
                ImageUtils.saveFile2SdCard(App.sContext, task.getFile(), "yblog");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (transfer != null) {
            transfer.destroy();
        }
        if (task != null) {
            task.cancel();
        }
    }
}
