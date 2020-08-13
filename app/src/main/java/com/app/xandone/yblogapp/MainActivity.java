package com.app.xandone.yblogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.model.ArticleModel;
import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ImageLoadHelper.getInstance().display(App.sContext, "http://www.xandone.pub/1596612465219", imageView);
        ATest a = new ATest();
        getLifecycle().addObserver(a);


        ArticleModel articleModel = ModelProvider.getModel(this, ArticleModel.class, App.sContext);
        articleModel.getLiveData().observe(this, new Observer<List<ArticleBean>>() {
            @Override
            public void onChanged(List<ArticleBean> list) {
                LogHelper.d(JsonUtils.obj2Json(list.get(0).getTitle()));
            }
        });

    }

}
