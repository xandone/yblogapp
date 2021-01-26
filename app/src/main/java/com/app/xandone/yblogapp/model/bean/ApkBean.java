package com.app.xandone.yblogapp.model.bean;

/**
 * author: Admin
 * created on: 2021/1/25 17:32
 * description:
 */
public class ApkBean {
    private int id;
    private String versionCode;
    private String versionName;
    private String versionTip;
    private String postTime;
    private int isForce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
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

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }
}
