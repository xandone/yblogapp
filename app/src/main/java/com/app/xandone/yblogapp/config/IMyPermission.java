package com.app.xandone.yblogapp.config;

import android.Manifest;

/**
 * author: Admin
 * created on: 2020/9/23 16:49
 * description:
 */
public interface IMyPermission {
    /**
     * 读写
     */
    int RC_WRITE_AND_READ_PERM_CODE = 100;
    /**
     * 相册
     */
    int RC_BROWSE_THE_CAMERA_CODE = 101;

    String[] WRITE_AND_READ_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    String BROWSE_THE_CAMERA = Manifest.permission.CAMERA;
}
