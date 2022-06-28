package com.app.xandone.yblogapp.db;

import com.app.xandone.yblogapp.db.dao.ReadRecordDao;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author: xiao
 * created on: 2022/6/28 11:04
 * description:
 */
@Database(entities = {CodeArticleBean.class},
        version = 1)
public abstract class BlogDatabase extends RoomDatabase {
    public abstract ReadRecordDao recodeDao();
}
