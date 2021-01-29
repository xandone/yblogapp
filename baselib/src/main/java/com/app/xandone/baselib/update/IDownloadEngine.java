package com.app.xandone.baselib.update;

/**
 * author: Admin
 * created on: 2021/1/25 17:10
 * description:
 */
public interface IDownloadEngine {
    void download(String url);

    void error(int code);
}
