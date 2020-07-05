package com.stjy.baselib.base.mvc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: ViewPager+Fragment+实现懒加载数据fragment基类
 */
abstract class LazyLoadFragment : BaseFragment() {

    private var isInit = true
    private var isLoad = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        isCanLoadData()
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isLoad) {
            return
        }
        isCanLoadData()
    }

    private fun isCanLoadData() {
        if (!isInit) {
            return
        }
        if (userVisibleHint) {
            lazyLoad()
            isLoad = true
        } else {
            if (isLoad) {
                stopLoad()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isInit = false
        isLoad = false
    }

    open fun lazyLoad() {}

    open fun stopLoad() {}
}