package com.stjy.baselib.utils

import android.arch.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @author acb
 * @date 2018/6/13 14:42
 * @ClassName RxLifecycleUtils
 * @Description <这里用一句话描述这个类的作用>
</这里用一句话描述这个类的作用> */
class RxLifecycleUtils private constructor() {
    init {
        throw IllegalStateException("Can't instance the RxLifecycleUtils")
    }

    companion object {
        fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner?): LifecycleTransformer<T> {
            return AndroidLifecycle.createLifecycleProvider(lifecycleOwner).bindToLifecycle()
        }
    }

}