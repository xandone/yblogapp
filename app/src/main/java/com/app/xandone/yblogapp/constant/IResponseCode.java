package com.app.xandone.yblogapp.constant;

/**
 * author: Admin
 * created on: 2020/8/13 16:58
 * description:
 */
public interface IResponseCode {
    //成功
    int SUCCESS = 200;
    //token失效
    int TOKEN_FAIL = 203;

    /**
     * 图片上传失败
     */
    int UPLOAD_PIC_EXCEPTION = 204;
}
