package com.app.xandone.yblogapp.model.bean;


/**
 * author: Admin
 * created on: 2020/9/8 10:36
 * description:
 */
public class BannerBean {
    /**
     * userId : 250
     * articelId : 157862322787472
     * title : 在一个有猩猩的夜晚
     * imgUrl : http://www.xandone.pub/FlznFRKRXKxata2F48tbh3R0CtAn
     * articleUrl : 157862322787472
     * pageViews : 9
     * upTime : 2020-01-10 10:05:06
     */

    private String userId;
    private String articelId;
    private String title;
    private String imgUrl;
    private String articleUrl;
    private int pageViews;
    private String upTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticelId() {
        return articelId;
    }

    public void setArticelId(String articelId) {
        this.articelId = articelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public int getPageViews() {
        return pageViews;
    }

    public void setPageViews(int pageViews) {
        this.pageViews = pageViews;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

}
