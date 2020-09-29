package com.app.xandone.yblogapp.model.event;

/**
 * author: Admin
 * created on: 2020/9/29 10:02
 * description:
 */
public class SwitchEvent {
    private int tag;

    public static final int MANAGER_LOGIN_RAG = 1;
    public static final int MANAGER_DATA_RAG = 2;

    public SwitchEvent(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
