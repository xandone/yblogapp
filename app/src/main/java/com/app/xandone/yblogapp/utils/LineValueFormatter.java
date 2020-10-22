package com.app.xandone.yblogapp.utils;

import com.app.xandone.yblogapp.model.bean.ArtInfoBean;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * author: Admin
 * created on: 2020/10/22 10:37
 * description:
 */
public class LineValueFormatter extends ValueFormatter {
    private final List<Integer> mLabels;
    private List<Integer> xAxisValues;


    public LineValueFormatter(List<Integer> labels, List<Integer> xAxisValues) {
        mLabels = labels;
        this.xAxisValues = xAxisValues;
    }

    @Override
    public String getPointLabel(Entry entry) {
        return mLabels.get(getIndex(entry.getX())) + "";
    }

    private int getIndex(float x) {
        if (xAxisValues == null) {
            return -1;
        }
        for (int i = 0; i < xAxisValues.size(); i++) {
            if (x == xAxisValues.get(i)) {
                return i;
            }
        }
        return 0;
    }
}