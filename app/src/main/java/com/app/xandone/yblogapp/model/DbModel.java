package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.db.BlogDb;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: xiao
 * created on: 2022/6/28 15:00
 * description:
 */
public class DbModel extends BaseViewModel {
    @Override
    protected void onCreate(LifecycleOwner owner) {

    }

    public void insertDb(CodeArticleBean bean) {
        addSubscrible(Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        BlogDb.create().recodeDao().insertCode(bean);
                    }
                }));
    }

    public void getAllRecordCodes(DbDataCall<List<CodeArticleBean>> call) {
        addSubscrible(Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        call.onDbdata(BlogDb.create().recodeDao().getAllCodes());
                    }
                }));
    }


    public interface DbDataCall<T> {
        void onDbdata(T t);
    }

}
