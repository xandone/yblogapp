package com.app.xandone.yblogapp.base;

import android.view.View;

import com.app.xandone.yblogapp.databinding.Frag1Binding;

/**
 * @author: xiao
 * created on: 2022/6/21 17:09
 * description:
 */
public class TestFragment extends BaseWallFragment<Frag1Binding> {
    @Override
    public View getLayout() {
        mBinding = Frag1Binding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void init(View view) {
        onLoadFinish();
        mBinding.btn32.setText("ghgjjhgdfsa");
    }

    @Override
    protected void requestData() {

    }
}
