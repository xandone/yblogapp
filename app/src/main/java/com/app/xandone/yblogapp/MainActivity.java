package com.app.xandone.yblogapp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ImageLoadHelper.getInstance().display(App.sContext, "http://www.xandone.pub/1596612465219", imageView);
    }

}
