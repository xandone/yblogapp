package com.app.xandone.yblogapp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        ImageLoadHelper.getInstance().display(App.sContext, "http://www.xandone.pub/1596612465219", imageView);

        Glide.with(this).load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1826397436,1732740655&fm=26&gp=0.jpg").into(imageView);

    }
}
