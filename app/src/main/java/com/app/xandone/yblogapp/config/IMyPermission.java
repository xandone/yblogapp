package com.app.xandone.yblogapp.config;

import android.Manifest;

/**
 * author: Admin
 * created on: 2020/9/23 16:49
 * description:
 */
public interface IMyPermission {

    String[] WRITE_AND_READ_PERMS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String BROWSE_THE_CAMERA = Manifest.permission.CAMERA;
}
