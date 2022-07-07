package com.app.xandone.yblogapp.ui.manager.admin;


import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.widgetlib.utils.SizeUtils;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.widgetlib.view.UserCircleView;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListActivity;
import com.app.xandone.yblogapp.cache.UserInfoHelper;
import com.app.xandone.yblogapp.model.AdminModel;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * author: Admin
 * created on: 2021/1/20 14:17
 * description:管理员列表
 */
public class AdminListActivity extends BaseListActivity {
    private BaseQuickAdapter<AdminBean, BaseViewHolder> mAdapter;
    private AdminModel adminModel;
    private List<AdminBean> datas;

    @Override
    public void init() {
        mAdapter = new BaseQuickAdapter<AdminBean, BaseViewHolder>(R.layout.item_admin_list, datas) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, AdminBean adminBean) {
                UserCircleView adminIconUv = helper.getView(R.id.admin_icon_uv);
                ImageLoadHelper.getInstance().display(AdminListActivity.this, adminBean.getAdminIcon(), adminIconUv);
                helper.setText(R.id.admin_name_tv, adminBean.getNickname());
                helper.setText(R.id.admin_identity_tv, adminBean.getIdentity());
                helper.setText(R.id.admin_last_login_tv, adminBean.getLastLoginTime());
            }
        };

        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.addItemDecoration(new SpacesItemDecoration(App.sContext, 10, 10));
        recycler.setAdapter(mAdapter);
        recycler.setPadding(0, 0, SizeUtils.dp2px(App.sContext, 10), 0);

        //一次性加载所有，因此不允许上拉
        unableLoadMore();

        adminModel = ModelProvider.getModel(this, AdminModel.class, App.sContext);
        requestData();
    }

    @Override
    protected void requestData() {
        getAdminList();
    }

    @Override
    public void getApiData() {
        getAdminList();
    }

    @Override
    public void getApiDataMore() {

    }

    private void getAdminList() {
        adminModel.getAdminList(UserInfoHelper.getAdminId(), new IRequestCallback<List<AdminBean>>() {
            @Override
            public void success(List<AdminBean> list) {
                onLoadFinish();
                finishRefresh();
                if (list == null) {
                    onLoadNetError();
                    return;
                }
                if (SimpleUtils.isEmpty(list)) {
                    onLoadEmpty();
                    return;
                }
                datas = list;
                mAdapter.setList(datas);
            }

            @Override
            public void error(String message, int statusCode) {
                onLoadStatus(statusCode);
            }
        });
    }
}
