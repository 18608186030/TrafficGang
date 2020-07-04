package com.stjy.baselib.base.mvp

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:  MVP模式Presenter基类
 */
open class BasePresenter<V> {

    private var mViewRef: Reference<V?>? = null

    fun attachView(view: V) {
        mViewRef = WeakReference(view)
    }

    fun detachView() {
        if (mViewRef != null) {
            mViewRef?.clear()
            mViewRef = null
        }
    }

    val view: V?
        get() = if (mViewRef != null) mViewRef?.get() else null

    val isViewAttached: Boolean
        get() = mViewRef != null && mViewRef?.get() != null
}