package com.app.xandone.yblogapp.cache;

import android.text.TextUtils;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.bean.AdminBean;

/**
 * author: Admin
 * created on: 2020/10/21 17:27
 * description:
 */
public class UserInfoHelper {
    public static boolean isAdminCache() {
        return !SimpleUtils.isEmpty(SpHelper.getDefaultValue(App.sContext, ISpKey.ADMIN_INFO_KEY,""));
    }

    public static String getAdminId() {
        String adminJson = SpHelper.getDefaultValue(App.sContext, ISpKey.ADMIN_INFO_KEY,"");
        if (TextUtils.isEmpty(adminJson)) {
            return "";
        }
        return JsonUtils.json2Obj(adminJson, AdminBean.class).getAdminId();
    }

    public static String getAdminToken() {
        String adminJson = SpHelper.getDefaultValue(App.sContext, ISpKey.ADMIN_INFO_KEY,"");
        return JsonUtils.json2Obj(adminJson, AdminBean.class).getToken();
    }

    public static AdminBean getAdminBean() {
        String adminJson = SpHelper.getDefaultValue(App.sContext, ISpKey.ADMIN_INFO_KEY,"");
        if (TextUtils.isEmpty(adminJson)) {
            return null;
        }
        return JsonUtils.json2Obj(adminJson, AdminBean.class);
    }
}
