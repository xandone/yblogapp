package com.app.xandone.baselib.update;

/**
 * author: Admin
 * created on: 2021/1/26 09:47
 * description:
 */
public class UpdateInfo {

    private int id;
    private int versionCode;
    private String versionName;
    private String versionTip;
    private String postTime;
    private String apkUrl;
    private boolean isForce;

    public UpdateInfo() {
    }

    public UpdateInfo(int id, int versionCode, String versionName, String versionTip,
                      String postTime, String apkUrl, boolean isForce) {
        this.id = id;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.versionTip = versionTip;
        this.postTime = postTime;
        this.apkUrl = apkUrl;
        this.isForce = isForce;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionTip() {
        return versionTip;
    }

    public void setVersionTip(String versionTip) {
        this.versionTip = versionTip;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }
}
