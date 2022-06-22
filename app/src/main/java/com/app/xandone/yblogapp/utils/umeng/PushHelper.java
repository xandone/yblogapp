package com.app.xandone.yblogapp.utils.umeng;

import android.content.Context;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.BuildConfig;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.constant.PushConstants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.api.UPushRegisterCallback;

/**
 * @author: xiao
 * created on: 2022/6/22 11:23
 * description:
 */
public class PushHelper {
    public static void preInit(Context context) {
        UMConfigure.preInit(context, PushConstants.UMENG_APP_KEY, PushConstants.UMENG_PUSH_CHANNEL);
    }

    public static void init(Context context) {
        UMConfigure.init(context,
                PushConstants.UMENG_APP_KEY,
                PushConstants.UMENG_PUSH_CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE,
                PushConstants.UMENG_MESSAGE_SECRET);
        //注册推送服务，每次调用register方法都会回调该接口
        PushAgent.getInstance(context)
                .register(new UPushRegisterCallback() {
                    @Override
                    public void onSuccess(String deviceToken) {
                        //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                        LogHelper.i("注册成功 deviceToken:" + deviceToken);
                    }

                    @Override
                    public void onFailure(String errCode, String errDesc) {
                        LogHelper.e("注册失败 " + "code:" + errCode + ", desc:" + errDesc);
                    }
                });
    }

    public static void initUmengSDK(App app) {
        //日志开关
        if (BuildConfig.DEBUG) {
            UMConfigure.setLogEnabled(true);
        }
        //预初始化
        preInit(app);
        //判断是否已同意隐私政策
        if (!isAgreePrivacy()) {
            return;
        }
        boolean isMainProcess = UMUtils.isMainProgress(app);
        if (isMainProcess) {
            //App启动速度优化：可以在子线程中调用SDK初始化接口
            new Thread(new Runnable() {
                @Override
                public void run() {
                    init(app.getApplicationContext());
                }
            }).start();
        }
    }


    /**
     * 是否同意了协议
     *
     * @return
     */
    public static boolean isAgreePrivacy() {
        return SpHelper.getDefaultBoolean(App.sContext, ISpKey.IS_AGREE_PRIVACY);
    }

    /**
     * 同意协议
     */
    public static void agreePrivacy2Local() {
        SpHelper.save2DefaultSp(App.sContext, ISpKey.IS_AGREE_PRIVACY, true);
    }

}
