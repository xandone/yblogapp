package com.app.xandone.yblogapp.ui.code;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallFragment;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.CodeTypeModel;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.event.CodeTypeEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.google.gson.reflect.TypeToken;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/3 09:56
 * description:
 */
public class CodeFragment extends BaseWallFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<Fragment> fragments;
    private SheetTypeFragment mSheetTypeFragment;
    private CodeTypeModel mCodeTypeModel;
    private ArrayList<CodeTypeBean> codeTypeList;
    private ArrayList<CodeTypeBean> apiTypeList;
    private ArrayList<CodeTypeBean> removeTypes;
    private CommonNavigatorAdapter mTabLayoutAdapter;
    private MyViewPagerAdapter vpAdapter;

    @Override
    public int getLayout() {
        return R.layout.frag_code;
    }

    @Override
    public void init(View view) {
        apiTypeList = new ArrayList<>();
        codeTypeList = new ArrayList<>();
        removeTypes = new ArrayList<>();
    }


    @Override
    protected void initDataObserver() {
        mCodeTypeModel = ModelProvider.getModel(mActivity, CodeTypeModel.class, App.sContext);

        requestData();
    }

    @Override
    protected void requestData() {
        mCodeTypeModel.getCodeTypeDatas(new IRequestCallback<List<CodeTypeBean>>() {
            @Override
            public void success(List<CodeTypeBean> codeTypeBeans) {
                initType(codeTypeBeans);
                onLoadFinish();
            }

            @Override
            public void error(String message, int statusCode) {
                onLoadStatus(statusCode);
            }
        });
    }

    public void initType(List<CodeTypeBean> codeTypeBeans) {
        apiTypeList.addAll(codeTypeBeans);
        dealCacheType();
        initTabLayout();

        fragments = new ArrayList<>();
        for (int i = 0; i < codeTypeList.size(); i++) {
            fragments.add(CodeListFragment.getInstance(codeTypeList.get(i).getType()));
        }
        vpAdapter = new MyViewPagerAdapter(getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(vpAdapter);
    }

    private void initTabLayout() {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        mTabLayoutAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return codeTypeList == null ? 0 : codeTypeList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                colorTransitionPagerTitleView.setText(codeTypeList.get(index).getTypeName());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                return indicator;
            }
        };
        commonNavigator.setAdapter(mTabLayoutAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    public void showDialogFrag() {
        mSheetTypeFragment = SheetTypeFragment.getInstance(codeTypeList, removeTypes);
        mSheetTypeFragment.show(getChildFragmentManager(), "demoBottom");
    }

    @OnClick({R.id.add_type_iv})
    public void click(View view) {
        showDialogFrag();
    }

    private void dealCacheType() {
        if (apiTypeList == null) {
            return;
        }
        String codeStr = SpHelper.getDefaultString(App.sContext, ISpKey.CODE_TYPE);
        List<CodeTypeBean> cacheList = JsonUtils.json2List(codeStr,
                new TypeToken<List<CodeTypeBean>>() {
                }.getType());
        if (cacheList == null || cacheList.size() == 0) {
            codeTypeList.addAll(apiTypeList);
            return;
        }

        for (CodeTypeBean bean : cacheList) {
            for (CodeTypeBean bean2 : apiTypeList) {
                if (bean.getType() == bean2.getType()) {
                    codeTypeList.add(bean);
                    break;
                }
            }
        }
        for (CodeTypeBean bean : apiTypeList) {
            boolean hasId = false;
            for (CodeTypeBean bean2 : codeTypeList) {
                if (bean.getType() == bean2.getType()) {
                    hasId = true;
                    break;
                }
            }
            if (!hasId) {
                removeTypes.add(bean);
            }
        }
        SpHelper.save2DefaultSp(App.sContext, ISpKey.CODE_TYPE, JsonUtils.obj2Json(codeTypeList));
    }


    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        MyViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCodeTypeEvent(CodeTypeEvent event) {
        if (event == null || event.getList() == null) {
            return;
        }
        codeTypeList.clear();
        codeTypeList.addAll(event.getList());
        fragments.clear();
        for (int i = 0; i < codeTypeList.size(); i++) {
            fragments.add(CodeListFragment.getInstance(codeTypeList.get(i).getType()));
        }
        mTabLayoutAdapter.notifyDataSetChanged();
        vpAdapter.notifyDataSetChanged();

    }
}
