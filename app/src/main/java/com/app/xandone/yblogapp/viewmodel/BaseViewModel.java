package com.app.xandone.yblogapp.viewmodel;



import com.app.xandone.yblogapp.rx.IManagerDisposable;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * author: Admin
 * created on: 2020/8/12 11:39
 * description:
 */
public abstract class BaseViewModel extends ViewModel implements LifecycleOwner, IManagerDisposable {

    private LifecycleOwner mLifecycleOwner;
    private CompositeDisposable mCompositeDisposable;

    protected abstract void onCreate(LifecycleOwner owner);

    <T extends BaseViewModel> T attachLifecycleOwner(LifecycleOwner lifecycleOwner) {
        T current = (T) this;
        if (mLifecycleOwner != null) {
            return current;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        this.mLifecycleOwner = lifecycleOwner;

        onCreate(lifecycleOwner);

        return current;

    }

    @Override
    public void addSubscrible(Disposable disposable) {
        if (disposable == null) {
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearSubscrible() {
        if (mCompositeDisposable == null) {
            return;
        }
        mCompositeDisposable.clear();
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleOwner.getLifecycle();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clearSubscrible();
    }
}
