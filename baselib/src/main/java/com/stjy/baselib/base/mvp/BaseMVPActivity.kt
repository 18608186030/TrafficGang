package com.stjy.baselib.base.mvp

import com.stjy.baselib.base.mvc.BaseActivity

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: MVP模式Activity基类
 */
abstract class BaseMVPActivity<V, P : BasePresenter<V>?> : BaseActivity() {
    var mPresenter: P? = null
    protected abstract fun createPresenter(): P?

    override fun setContentView(layoutResID: Int) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        super.setContentView(layoutResID)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}