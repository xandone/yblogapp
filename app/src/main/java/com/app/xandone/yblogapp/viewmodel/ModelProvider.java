package com.app.xandone.yblogapp.viewmodel;


import android.app.Application;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * author: Admin
 * created on: 2020/8/12 14:40
 * description:
 */
public class ModelProvider {

    public static <T extends BaseViewModel> T getModel(FragmentActivity activity, Class<T> viewModel, Application application) {
        return new ViewModelProvider(activity, new ViewModelProvider.AndroidViewModelFactory(application))
                .get(viewModel)
                .attachLifecycleOwner(activity);
    }
}
