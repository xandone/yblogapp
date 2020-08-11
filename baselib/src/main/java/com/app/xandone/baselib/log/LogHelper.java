package com.app.xandone.baselib.log;

import com.app.xandone.baselib.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;

/**
 * author: Admin
 * created on: 2020/8/11 11:32
 * description:
 */
public class LogHelper {

    public static final int ENGINE_LOGGER = 1;

    private static int logEngine = ENGINE_LOGGER;

    private LogHelper() {
    }

    public LogHelper getInstance() {
        return Build.logHelper;
    }

    private static class Build {
        private static LogHelper logHelper = new LogHelper();
    }

    public static void init(int engineType) {
        logEngine = engineType;
        if (logEngine == ENGINE_LOGGER) {
            Logger.addLogAdapter(new AndroidLogAdapter() {
                @Override
                public boolean isLoggable(int priority, @Nullable String tag) {
                    return BuildConfig.DEBUG;
                }
            });
        }
    }

    public static void d(Object log) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.d(log);
        }
    }

    public static void d(String msg, Object... args) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.d(msg, args);
        }
    }

    public static void e(String msg, Object... args) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.e(msg, args);
        }
    }

    public static void w(String msg) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.w(msg);
        }
    }

    public static void v(String msg) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.v(msg);
        }
    }

    public static void i(String msg) {
        if (logEngine == ENGINE_LOGGER) {
            Logger.i(msg);
        }
    }


}
