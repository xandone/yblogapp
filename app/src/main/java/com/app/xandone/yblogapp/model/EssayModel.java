package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.repository.CodeRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/9/6 21:29
 * description:
 */
public class EssayModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback;
    private IRequestCallback<List<BannerBean>> bannerCallback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getEssayArticleLiveData().observe(owner, new Observer<BaseResponse<List<EssayArticleBean>>>() {
            @Override
            public void onChanged(BaseResponse<List<EssayArticleBean>> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });

        articleRepo.getBannerLiveData().observe(owner, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> beans) {
                if (bannerCallback != null) {
                    bannerCallback.success(beans);
                }
            }
        });
    }

    public void getEssayDatas(int page, int row, IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback) {
        this.callback = callback;
        addSubscrible(articleRepo.getEssayDatas(page, row, callback));
    }

    public void getBannerDatas(IRequestCallback<List<BannerBean>> callback) {
        this.bannerCallback = callback;
        addSubscrible(articleRepo.getBannerDatas(callback));
    }
}
