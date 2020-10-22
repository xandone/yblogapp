package com.app.xandone.baselib.utils;

import android.os.Build;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import androidx.collection.SimpleArrayMap;

/**
 * author: Admin
 * created on: 2020/10/22 10:06
 * description:
 */
public class SimpleUtils {
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        } else if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        } else if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        } else if (Build.VERSION.SDK_INT >= 18 && obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        } else {
            return Build.VERSION.SDK_INT >= 16 && obj instanceof android.util.LongSparseArray && ((android.util.LongSparseArray) obj).size() == 0;
        }
    }
}
