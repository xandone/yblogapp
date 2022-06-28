package com.app.xandone.yblogapp.model.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author: Admin
 * created on: 2020/8/12 16:48
 * description:
 */
@Entity(tableName = "codes")
public class CodeArticleBean {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String artId;
    private String artUserId;
    private String title;
    private int artCommentCount;
    private int artBrowseCount;
    private int type;
    private String typeName;
    private String coverImg;
    private String postTime;
    private String content;
    private String contentHtml;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getArtUserId() {
        return artUserId;
    }

    public void setArtUserId(String artUserId) {
        this.artUserId = artUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtCommentCount() {
        return artCommentCount;
    }

    public void setArtCommentCount(int artCommentCount) {
        this.artCommentCount = artCommentCount;
    }

    public int getArtBrowseCount() {
        return artBrowseCount;
    }

    public void setArtBrowseCount(int artBrowseCount) {
        this.artBrowseCount = artBrowseCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
}
