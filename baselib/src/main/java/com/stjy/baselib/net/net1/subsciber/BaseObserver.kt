package com.stjy.baselib.net.net1.subsciber

import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.utils.HttpLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.EndConsumerHelper
import java.util.concurrent.atomic.AtomicReference

/**
 * @author acb
 * @date 2018/5/28 17:56
 * @ClassName BaseObserver
 * @Description
 */
abstract class BaseObserver<T> : Observer<T> {
    @JvmField
    var disposable: Disposable? = null
    val upstream = AtomicReference<Disposable>()
    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (EndConsumerHelper.setOnce(upstream, d, javaClass)) {
            onStart()
        }
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is ApiException -> {
                onError(e)
            }
            else -> {
                onError(ApiException.handleException(e))
            }
        }
    }

    override fun onComplete() {
    }

    /**
     * 请求开始
     */
    open fun onStart() {}

    /**
     * 请求成功
     *
     * @param t 数据
     */
    open fun onSuccess(t: T){}

    /**
     * 请求异常
     *
     * @param e 异常类型
     */
    open fun onError(e: ApiException){}
}