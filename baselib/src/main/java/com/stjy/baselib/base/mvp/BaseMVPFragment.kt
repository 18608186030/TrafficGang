package com.stjy.baselib.base.mvp

import android.os.Bundle
import com.stjy.baselib.base.mvc.BaseFragment

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: MVP模式fragment基类
 */
abstract class BaseMVPFragment<V, P : BasePresenter<V>?> : BaseFragment() {
    var mPresenter: P? = null
    protected abstract fun createPresenter(): P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}