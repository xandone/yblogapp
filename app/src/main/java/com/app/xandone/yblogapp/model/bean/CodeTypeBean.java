package com.app.xandone.yblogapp.model.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * author: Admin
 * created on: 2020/9/9 17:36
 * description:
 */
public class CodeTypeBean implements Parcelable, Serializable {
    /**
     * count : 2
     * typeName : 编程
     * type : 0
     */

    private int count;
    private String typeName;
    private int type;

    public CodeTypeBean(String typeName, int type) {
        this.typeName = typeName;
        this.type = type;
    }

    protected CodeTypeBean(Parcel in) {
        count = in.readInt();
        typeName = in.readString();
        type = in.readInt();
    }

    public static final Creator<CodeTypeBean> CREATOR = new Creator<CodeTypeBean>() {
        @Override
        public CodeTypeBean createFromParcel(Parcel in) {
            return new CodeTypeBean(in);
        }

        @Override
        public CodeTypeBean[] newArray(int size) {
            return new CodeTypeBean[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(typeName);
        dest.writeInt(type);
    }
}
