package com.stjy.baselib.base.mvp

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:  MVP模式View基类
 */
interface IBaseView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showLoadingStateView()
    fun hideLoadingStateView()
}