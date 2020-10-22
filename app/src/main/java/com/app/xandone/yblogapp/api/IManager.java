package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.model.bean.ArtInfoBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import io.reactivex.disposables.Disposable;

/**
 * author: Admin
 * created on: 2020/9/27 11:31
 * description:
 */
public interface IManager {

    Disposable login(String name, String psw, IRequestCallback<AdminBean> callback);

    MediatorLiveData<AdminBean> getAdminLiveData();

    Disposable getArtInfoData(String id, IRequestCallback<ArtInfoBean> callback);

    MediatorLiveData<ArtInfoBean> getArtInfoLiveData();
}
