package com.app.xandone.baselib.view.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.app.xandone.baselib.R;
import com.app.xandone.baselib.utils.SimpleUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author: Admin
 * created on: 2021/5/10 18:44
 * description:
 */
public class FilterFragment extends Fragment implements View.OnClickListener {
    private RecyclerView filterRv;
    private TextView btReset;
    private TextView btConfirm;
    private int type;
    private ArrayList<Filter> filters;

    private FilterAdapter mFilterAdapter;

    private Map<String, Object> paramsMap;

    public static final String TYPE_KEY = "filter_type_key";
    public static final String DATA_KEY = "filter_data_key";

    public static String IS_FILTER = "FilterFragment_isFilter";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_filter_layout, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        filterRv = view.findViewById(R.id.filter_rv);
        btReset = view.findViewById(R.id.bt_reset);
        btConfirm = view.findViewById(R.id.bt_confirm);

        btReset.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

        initView(view);
    }

    protected void initView(View view) {
        if (getArguments() == null) {
            return;
        }
        type = getArguments().getInt(TYPE_KEY);
        paramsMap = new HashMap<>();
        paramsMap.put(TYPE_KEY, type);
        filters = (ArrayList<Filter>) getArguments().getSerializable(DATA_KEY);
        initAdapter();
    }

    private void initAdapter() {
        if (filters == null) {
            return;
        }
        mFilterAdapter = new FilterAdapter(filters, paramsMap);
        filterRv.setAdapter(mFilterAdapter);
        filterRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void resetFilter() {
        for (int i = 0; i < filters.size(); i++) {
            FilterItem[] arr = filters.get(i).getFilterItems();
            switch (filters.get(i).getItemType()) {
                case Filter.FILTER_SEARCH:
                    paramsMap.put(Filter.FILTER_SEARCH_KEY, filters.get(i).getFilterItems()[0].getItemValue());
                    paramsMap.put(Filter.FILTER_SEARCH_CONTENT, "");
                    break;
                case Filter.FILTER_GRID:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    for (int j = 0; j < arr.length; j++) {
                        filters.get(i).getFilterItems()[j].setSelected(false);
                    }
                    filters.get(i).getFilterItems()[0].setSelected(true);
                    paramsMap.put(Filter.FILTER_GRID_KEY, filters.get(i).getFilterItems()[0].getItemValue());
                    break;
                case Filter.FILTER_GRID_2:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    for (int j = 0; j < arr.length; j++) {
                        filters.get(i).getFilterItems()[j].setSelected(false);
                    }
                    filters.get(i).getFilterItems()[0].setSelected(true);
                    paramsMap.put(Filter.FILTER_GRID_KEY_2, filters.get(i).getFilterItems()[0].getItemValue());
                    break;
                case Filter.FILTER_DATE:
                    paramsMap.put(Filter.FILTER_DATE_START_KEY, "");
                    paramsMap.put(Filter.FILTER_DATE_END_KEY, "");
                    break;
                case Filter.FILTER_DATE_2:
                    paramsMap.put(Filter.FILTER_DATE_START_KEY_2, "");
                    paramsMap.put(Filter.FILTER_DATE_END_KEY_2, "");
                    break;
                case Filter.FILTER_CHOOSE_1:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    paramsMap.put(Filter.FILTER_CHOOSE_KEY_1, arr[0].getItemValue());
                    break;
                case Filter.FILTER_CHOOSE_2:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    paramsMap.put(Filter.FILTER_CHOOSE_KEY_2, arr[0].getItemValue());
                    break;
                default:
                    break;
            }
        }

        initAdapter();
    }

    private void commitFilter() {
        paramsMap.put(IS_FILTER, isFilterData());
        EventBus.getDefault().post(paramsMap);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        //重置条件
        if (id == R.id.bt_reset) {
            resetFilter();
            //确定
        } else if (id == R.id.bt_confirm) {
            commitFilter();
        }
    }

    /**
     * 是否进行了筛选
     */
    private boolean isFilterData() {
        for (int i = 0; i < filters.size(); i++) {
            FilterItem[] arr = filters.get(i).getFilterItems();
            switch (filters.get(i).getItemType()) {
                case Filter.FILTER_SEARCH:
                    if (isChange(filters.get(i).getFilterItems()[0].getItemValue(), paramsMap.get(Filter.FILTER_SEARCH_KEY)) ||
                            isChange("", paramsMap.get(Filter.FILTER_SEARCH_CONTENT))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_GRID:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    if (isChange(filters.get(i).getFilterItems()[0].getItemValue(), paramsMap.get(Filter.FILTER_GRID_KEY))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_GRID_2:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    if (isChange(filters.get(i).getFilterItems()[0].getItemValue(), paramsMap.get(Filter.FILTER_GRID_KEY_2))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_DATE:
                    if (isChange("", paramsMap.get(Filter.FILTER_DATE_START_KEY)) ||
                            isChange("", paramsMap.get(Filter.FILTER_DATE_END_KEY))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_DATE_2:
                    if (isChange("", paramsMap.get(Filter.FILTER_DATE_START_KEY_2)) ||
                            isChange("", paramsMap.get(Filter.FILTER_DATE_END_KEY_2))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_CHOOSE_1:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    if (isChange(arr[0].getItemValue(), paramsMap.get(Filter.FILTER_CHOOSE_KEY_1))) {
                        return true;
                    }
                    break;
                case Filter.FILTER_CHOOSE_2:
                    if (SimpleUtils.isEmpty(arr)) {
                        break;
                    }
                    if (isChange(arr[0].getItemValue(), paramsMap.get(Filter.FILTER_CHOOSE_KEY_2))) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }

        return false;

    }

    /**
     * 判断map的某个值是否和原始值一样
     *
     * @param o1
     * @param mapObject
     * @return
     */
    private boolean isChange(Object o1, Object mapObject) {
        //null的时候，表面根本没有选择过，直接返回false
        if (mapObject == null) {
            return false;
        }
        return !Objects.equals(o1, mapObject);
    }

}
