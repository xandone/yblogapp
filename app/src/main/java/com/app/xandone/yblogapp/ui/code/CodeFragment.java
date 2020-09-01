package com.app.xandone.yblogapp.ui.code;

import android.view.View;
import android.widget.TextView;

import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.model.ArticleModel;
import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import java.util.List;

import androidx.lifecycle.Observer;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/8/31 17:55
 * description:
 */
public class CodeFragment extends BaseWallFragment {
    @BindView(R.id.code)
    TextView code;

    @Override
    public int getLayout() {
        return R.layout.frag_code;
    }

    @Override
    public void init(View view) {
        ArticleModel articleModel = ModelProvider.getModel(mActivity, ArticleModel.class, App.sContext)
                .getArticleDatas();
        articleModel.getLiveData().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> articleBeans) {
                code.setText(articleBeans.get(0).getTitle());
                loadFinish();
            }
        });
    }
}
