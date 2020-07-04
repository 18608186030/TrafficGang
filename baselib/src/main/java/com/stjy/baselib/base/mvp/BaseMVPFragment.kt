package com.stjy.baselib.base.mvp

import android.os.Bundle
import com.stjy.baselib.base.cmmont.BaseFragment

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: MVP模式fragment基类
 */
abstract class BaseMVPFragment<V, P : BasePresenter<V>?> : BaseFragment(), IBaseView {
    var mPresenter: P? = null
        get() = mPresenter

    protected abstract fun createPresenter(): P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        super.onActivityCreated(savedInstanceState)
    }

    override fun showLoadingDialog() {
        startLoadingDialog()
    }

    override fun hideLoadingDialog() {
        stopLoadingDialog()
    }

    override fun showLoadingStateView() {
        startLoading()
    }

    override fun hideLoadingStateView() {
        stopLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}