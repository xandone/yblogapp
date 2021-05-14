package com.app.xandone.baselib.view.filter;

import java.io.Serializable;

/**
 * author: Admin
 * created on: 2021/5/11 09:47
 * description:
 */
public class FilterItem implements Serializable {
    private String itemName;
    private Object itemValue;
    private boolean isSelected;

    public FilterItem() {
    }

    public FilterItem(String itemName, Object itemValue, boolean isSelected) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.isSelected = isSelected;
    }

    public String getItemName() {
        return itemName;
    }

    public FilterItem setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public Object getItemValue() {
        return itemValue;
    }

    public FilterItem setItemValue(Object itemValue) {
        this.itemValue = itemValue;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public FilterItem setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }
}
