package com.stjy.baselib.base.mvp.demo

import com.stjy.baselib.base.mvp.IBaseView

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:  MvpDemo View类
 */
interface IViewMvpDemo : IBaseView {
    fun getListDataSuccess(data: String)
}