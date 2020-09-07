package com.app.xandone.yblogapp.ui.splash;

import com.app.xandone.baselib.base.BaseActivity;
import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.yblogapp.MainActivity;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.rx.RxHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author: Admin
 * created on: 2020/9/1 14:05
 * description:
 */
public class SplashActivity extends BaseSimpleActivity {
    private Disposable disposable;

    @Override
    public int getLayout() {
        return R.layout.act_splash;
    }

    @Override
    public void init() {
        disposable = Flowable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxHelper.handleIO())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(MainActivity.class);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
