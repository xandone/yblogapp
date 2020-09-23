package com.app.xandone.yblogapp.ui.splash;

import android.util.Log;

import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.yblogapp.MainActivity;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.config.IMyPermission;
import com.app.xandone.yblogapp.rx.RxHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * author: Admin
 * created on: 2020/9/1 14:05
 * description:
 */
public class SplashActivity extends BaseSimpleActivity implements EasyPermissions.PermissionCallbacks {
    private Disposable disposable;

    @Override
    public int getLayout() {
        return R.layout.act_splash;
    }

    @Override
    public void init() {
        writeAndReadTask();
    }

    private void go2NextPage() {
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

    private boolean hasWriteAndReadPermissions() {
        return EasyPermissions.hasPermissions(this, IMyPermission.WRITE_AND_READ_PERMS);
    }

    @AfterPermissionGranted(IMyPermission.RC_WRITE_AND_READ_PERM_CODE)
    public void writeAndReadTask() {
        if (hasWriteAndReadPermissions()) {
            // Have permissions, do the thing!
            go2NextPage();
        } else {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this,
                    IMyPermission.RC_WRITE_AND_READ_PERM_CODE,
                    IMyPermission.WRITE_AND_READ_PERMS)
                    .setRationale("需要以下权限")
                    .build());
//            EasyPermissions.requestPermissions(
//                    this,
//                    "需要以下权限",
//                    MyPermission.RC_WRITE_AND_READ_PERM_CODE,
//                    MyPermission.WRITE_AND_READ_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("gfgdfgdf","1111111");
        go2NextPage();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("gfgdfgdf","22222");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
//        AppManager.newInstance().finishAllActivity();
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
