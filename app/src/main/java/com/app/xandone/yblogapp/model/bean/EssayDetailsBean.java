package com.app.xandone.yblogapp.model.bean;

/**
 * author: Admin
 * created on: 2020/9/7 10:02
 * description:
 */
public class EssayDetailsBean {

    private String essayId;
    private String essayUserId;
    private String title;
    private int essayCommentCount;
    private int essayBrowseCount;
    private int type;
    private String coverImg;
    private String postTime;
    private String content;
    private String contentHtml;
    private int isTopping;

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public String getEssayUserId() {
        return essayUserId;
    }

    public void setEssayUserId(String essayUserId) {
        this.essayUserId = essayUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEssayCommentCount() {
        return essayCommentCount;
    }

    public void setEssayCommentCount(int essayCommentCount) {
        this.essayCommentCount = essayCommentCount;
    }

    public int getEssayBrowseCount() {
        return essayBrowseCount;
    }

    public void setEssayBrowseCount(int essayBrowseCount) {
        this.essayBrowseCount = essayBrowseCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public int getIsTopping() {
        return isTopping;
    }

    public void setIsTopping(int isTopping) {
        this.isTopping = isTopping;
    }
}
