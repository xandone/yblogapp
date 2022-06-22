package com.app.xandone.baselib.view.filter;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.xandone.baselib.R;
import com.app.xandone.baselib.utils.DatePickerUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * author: Admin
 * created on: 2021/5/11 09:47
 * description:
 */
public class FilterAdapter extends BaseMultiItemQuickAdapter<Filter, BaseViewHolder> {

    private Map<String, Object> paramsMap;

    public FilterAdapter(List<Filter> data, Map<String, Object> paramsMap) {
        super(data);
        this.paramsMap = paramsMap;
        addItemType(Filter.FILTER_SEARCH, R.layout.item_filter_search);
        addItemType(Filter.FILTER_GRID, R.layout.item_filter_grid);
        addItemType(Filter.FILTER_GRID_2, R.layout.item_filter_grid);
        addItemType(Filter.FILTER_CHOOSE_1, R.layout.item_filter_choose);
        addItemType(Filter.FILTER_CHOOSE_2, R.layout.item_filter_choose);
        addItemType(Filter.FILTER_DATE, R.layout.item_filter_date);
        addItemType(Filter.FILTER_DATE_2, R.layout.item_filter_date);

    }


    @Override
    protected void convert(BaseViewHolder helper, Filter item) {
        switch (helper.getItemViewType()) {
            case Filter.FILTER_SEARCH:
                convertSearch(helper, item, Filter.FILTER_SEARCH_KEY, Filter.FILTER_SEARCH_CONTENT);
                break;
            case Filter.FILTER_GRID:
                convertGrid(helper, item, Filter.FILTER_GRID_KEY);
                break;
            case Filter.FILTER_GRID_2:
                convertGrid(helper, item, Filter.FILTER_GRID_KEY_2);
                break;
            case Filter.FILTER_CHOOSE_1:
                convertChoose(helper, item, Filter.FILTER_CHOOSE_KEY_1);
                break;
            case Filter.FILTER_CHOOSE_2:
                convertChoose(helper, item, Filter.FILTER_CHOOSE_KEY_2);
                break;
            case Filter.FILTER_DATE:
                convertDate(helper, item, Filter.FILTER_DATE_START_KEY, Filter.FILTER_DATE_END_KEY);
                break;
            case Filter.FILTER_DATE_2:
                convertDate(helper, item, Filter.FILTER_DATE_START_KEY_2, Filter.FILTER_DATE_END_KEY_2);
                break;
            default:
                break;
        }
    }

    private void convertSearch(BaseViewHolder helper, Filter filter, String key, String keyContent) {
        helper.setText(R.id.filter_title_tv, filter.getFilterTitle());
        TextView keyNoTv = helper.getView(R.id.keyNo_tv);
        EditText searchEt = helper.getView(R.id.search_et);
        keyNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyNoPopup(keyNoTv, filter, key);
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                paramsMap.put(keyContent, s.toString());
            }
        });
    }

    private void convertGrid(BaseViewHolder helper, Filter filter, String key) {
        List<FilterItem> gridDatas = new ArrayList<>();
        BaseQuickAdapter<FilterItem, BaseViewHolder> gridAdapter = new BaseQuickAdapter<FilterItem, BaseViewHolder>(R.layout.item_filter_grid_info, gridDatas) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, FilterItem filterItem) {
                baseViewHolder.setText(R.id.grid_tv, filterItem.getItemName());
                baseViewHolder.getView(R.id.grid_tv).setSelected(filterItem.isSelected());
            }
        };
        helper.setText(R.id.filter_title_tv, filter.getFilterTitle());
        RecyclerView filterGridRv = helper.getView(R.id.filter_grid_rv);
        filterGridRv.setAdapter(gridAdapter);
        filterGridRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        gridDatas.clear();
        Collections.addAll(gridDatas, filter.getFilterItems());
        gridAdapter.notifyDataSetChanged();

        gridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                for (int i = 0; i < gridDatas.size(); i++) {
                    gridDatas.get(i).setSelected(false);
                }
                gridDatas.get(position).setSelected(true);
                paramsMap.put(key, gridDatas.get(position).getItemValue());
                gridAdapter.notifyDataSetChanged();
            }
        });
    }

    private void convertDate(BaseViewHolder helper, Filter filter, String startKey, String endKey) {
        helper.setText(R.id.filter_title_tv, filter.getFilterTitle());
        TextView startDateTv = helper.getView(R.id.start_date_tv);
        TextView startEndTv = helper.getView(R.id.end_date_tv);

        startDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.showDateWithNoHour(getContext(), new DatePickerUtils.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(String time) {
                        startDateTv.setText(time);
                        paramsMap.put(startKey, time);
                    }
                });
            }
        });

        startEndTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.showDateWithNoHour(getContext(), new DatePickerUtils.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(String time) {
                        startEndTv.setText(time);
                        paramsMap.put(endKey, time);
                    }
                });
            }
        });
    }


    private void convertChoose(BaseViewHolder helper, Filter filter, String key) {
        helper.setText(R.id.filter_title_tv, filter.getFilterTitle());
        TextView filterChooseTv = helper.getView(R.id.filter_choose_tv);
        filterChooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopue(filterChooseTv, filter, key);
            }
        });
    }

    private void showPopue(TextView filterChooseTv, Filter filter, String key) {
        FilterItem[] arr = filter.getFilterItems();
        List<String> list = new ArrayList<>();
        for (FilterItem item : filter.getFilterItems()) {
            list.add(item.getItemName());
        }
//        SlideFromTopPopup mPopup = new SlideFromTopPopup(getContext(), list);
//        mPopup.setBackgroundColor(Color.TRANSPARENT);
//        mPopup.showPopupWindow(filterChooseTv);
//        mPopup.setPopupGravity(Gravity.BOTTOM);
//        mPopup.setMaxHeight(SizeUtils.dp2px(160));
//        mPopup.setItemClickCallback(new SlideFromTopPopup.ItemClickCallback() {
//            @Override
//            public void click(View view, int position) {
//                mPopup.dismiss();
//                filterChooseTv.setText(list.get(position));
//                paramsMap.put(key, arr[position].getItemValue());
//            }
//        });
    }

    /**
     * 关键词搜索类型
     *
     * @param view
     */
    private void showKeyNoPopup(TextView view, Filter filter, String key) {
        FilterItem[] arr = filter.getFilterItems();
        String[] list = new String[arr.length];
        for (int i = 0; i < filter.getFilterItems().length; i++) {
            list[i] = arr[i].getItemName();
        }
//        new XPopup.Builder(getContext())
//                .hasShadowBg(false)
//                .atView(view)
//                .asAttachList(list,
//                        null,
//                        new OnSelectListener() {
//                            @Override
//                            public void onSelect(int position, String text) {
//                                view.setText(text);
//                                paramsMap.put(key, arr[position].getItemValue());
//                            }
//                        }, 0, 0).show();
    }

}
