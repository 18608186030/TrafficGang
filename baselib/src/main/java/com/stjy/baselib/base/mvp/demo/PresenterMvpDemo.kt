package com.stjy.baselib.base.mvp.demo

import com.stjy.baselib.base.mvp.BasePresenter

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:
 */
class PresenterMvpDemo() : BasePresenter<IViewMvpDemo?>() {
    fun getListData(parms: String?) {
        val data = "我是模拟网络请求后的数据"
        view?.getListDataSuccess(data)
    }
}