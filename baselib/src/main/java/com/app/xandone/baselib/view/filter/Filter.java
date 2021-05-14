package com.app.xandone.baselib.view.filter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * author: Admin
 * created on: 2021/5/11 09:47
 * description:
 */
public class Filter implements Serializable, MultiItemEntity {
    public static final int FILTER_SEARCH = 1;
    public static final int FILTER_GRID = 2;
    public static final int FILTER_CHOOSE_1 = 3;
    public static final int FILTER_DATE = 4;
    public static final int FILTER_CHOOSE_2 = 5;
    public static final int FILTER_GRID_2 = 6;
    public static final int FILTER_DATE_2 = 7;

    public static final String FILTER_SEARCH_KEY = "FILTER_SEARCH_KEY";
    public static final String FILTER_SEARCH_CONTENT = "FILTER_SEARCH_CONTENT";
    public static final String FILTER_GRID_KEY = "FILTER_GRID_KEY";
    public static final String FILTER_GRID_KEY_2 = "FILTER_GRID_KEY_2";
    public static final String FILTER_CHOOSE_KEY_1 = "FILTER_CHOOSE_KEY_1";
    public static final String FILTER_DATE_START_KEY = "FILTER_DATE_START_KEY";
    public static final String FILTER_DATE_END_KEY = "FILTER_DATE_END_KEY";
    public static final String FILTER_CHOOSE_KEY_2 = "FILTER_CHOOSE_KEY_1";
    public static final String FILTER_DATE_START_KEY_2 = "FILTER_DATE_START_KEY_2";
    public static final String FILTER_DATE_END_KEY_2 = "FILTER_DATE_END_KEY_2";

    private int viewType;
    private String filterTitle;
    private FilterItem[] filterItemsm;

    public Filter() {
    }

    public Filter(int viewType, String filterTitle, FilterItem[] filterItemsm) {
        this.viewType = viewType;
        this.filterTitle = filterTitle;
        this.filterItemsm = filterItemsm;
    }

    public int getViewType() {
        return viewType;
    }

    public Filter setViewType(int viewType) {
        this.viewType = viewType;
        return this;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public Filter setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
        return this;
    }

    public FilterItem[] getFilterItems() {
        return filterItemsm;
    }

    public Filter setFilterItems(FilterItem[] filterItems) {
        filterItemsm = filterItems;
        return this;
    }

    @Override
    public int getItemType() {
        return viewType;
    }

    static class Builder {

    }
}
