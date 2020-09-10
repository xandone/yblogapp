package com.app.xandone.yblogapp.model.event;

import com.app.xandone.yblogapp.model.bean.CodeTypeBean;

import java.util.List;

/**
 * author: Admin
 * created on: 2020/9/10 15:52
 * description:
 */
public class CodeTypeEvent {
    private List<CodeTypeBean> list;

    public CodeTypeEvent(List<CodeTypeBean> list) {
        this.list = list;
    }

    public List<CodeTypeBean> getList() {
        return list;
    }

    public void setList(List<CodeTypeBean> list) {
        this.list = list;
    }
}
