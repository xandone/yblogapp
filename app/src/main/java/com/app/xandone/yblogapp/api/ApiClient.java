package com.app.xandone.yblogapp.api;


import android.util.Log;

import com.app.xandone.baselib.cache.FileHelper;
import com.app.xandone.baselib.utils.NetworkUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.BuildConfig;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.config.ApiHost;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: Admin
 * created on: 2020/8/12 16:56
 * description:
 */

public class ApiClient {
    private Retrofit retrofit;

    private ApiClient() {
    }

    private ApiClient(String host, final boolean isCache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(FileHelper.getAppCacheDir(App.sContext));
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder().header("token",
                        UserInfoHelper.isAdminCache() ? UserInfoHelper.getAdminToken() : "").build();
                if (!NetworkUtils.isConnected(App.sContext) && isCache) {
                    int maxStale = 60 * 60 * 24 * 2;
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                } else {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .header("Cache-Control", "public, max-age=" + 0)
                            .build();
                }

                return chain.proceed(request);
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);

        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiClient getInstance() {
        return Builder.create(ApiHost.DEFAULT_HOST, true);
    }

    public static ApiClient getInstance(String host, boolean isCache) {
        return Builder.create(host, isCache);
    }

    public IApiService getApiService() {
        IApiService apiService = null;
        if (retrofit != null) {
            apiService = retrofit.create(IApiService.class);
        }
        return apiService;
    }

    private static class Builder {
        private static ApiClient netClient;

        static ApiClient create(String host, boolean isCache) {
            netClient = new ApiClient(host, isCache);
            return netClient;
        }
    }
}
