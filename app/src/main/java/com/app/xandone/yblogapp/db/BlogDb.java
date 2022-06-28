package com.app.xandone.yblogapp.db;

import com.app.xandone.baselib.cache.FileHelper;
import com.app.xandone.yblogapp.App;

import java.io.File;

import androidx.room.Room;

/**
 * @author: xiao
 * created on: 2022/6/28 11:14
 * description:
 */
public class BlogDb {
    public static final String DB_NAME = "yblog_db";
    String dir = FileHelper.getExternalFilesDir(App.sContext) + File.separator + DB_NAME;
    private static BlogDatabase db;

    public static BlogDatabase create() {
        if (db == null) {
            synchronized (BlogDb.class) {
                if (db == null) {
                    db = Room.databaseBuilder(App.sContext,
                            BlogDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return db;
    }
}
