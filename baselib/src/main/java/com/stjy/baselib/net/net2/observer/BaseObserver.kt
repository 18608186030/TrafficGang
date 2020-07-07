package com.stjy.baselib.net.net2.observer

import com.stjy.baselib.net.net2.resp.BaseResp
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T : BaseResp> : Observer<T> {
    override fun onComplete() {
    }

    override fun onSubscribe(disposable: Disposable) {
    }

    override fun onNext(data: T) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    /**
     * 非Success,都调用此方法
     */
    open fun onErrorHandle(msg: String?): Boolean {
        return false
    }

    open fun onSuccess(data: T) {

    }
}