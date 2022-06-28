package com.app.xandone.yblogapp.db.dao;

import com.app.xandone.yblogapp.model.bean.CodeArticleBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * @author: xiao
 * created on: 2022/6/28 11:07
 * description:
 */
@Dao
public interface ReadRecordDao {

    @Insert
    void insertCode(CodeArticleBean code);


    /**
     * 编写自己的 SQL 查询(query)方法
     *
     * @return
     */
    @Query("SELECT * FROM codes")
    List<CodeArticleBean> getAllCodes();
}
