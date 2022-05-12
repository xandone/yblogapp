package com.app.xandone.yblogapp.ui.code;


import com.app.xandone.yblogapp.model.base.BaseResponse;

import java.util.List;


/**
 * @author: Admin
 * created on: 2022/5/12 14:23
 * description:
 */
public interface IListAction<E> {

    void dealLoadSuccess(BaseResponse<List<E>> response, boolean isLoadMore);

    void dealLoadFail(String message, int statusCode);
}
