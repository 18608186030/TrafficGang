package com.stjy.baselib.net.net1.subsciber;

import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * @author acb
 * @date 2018/5/28 17:56
 * @ClassName BaseObserver
 * @Description
 */
public abstract class BaseObserver<T> implements Observer<T> {

    Disposable disposable;
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        if (EndConsumerHelper.setOnce(this.upstream, d, getClass())) {
            onStart();
        }
    }

    @Override
    public void onNext(T t) {
        HttpLog.e("-->http is onNext");
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        HttpLog.e("-->http is onError");
        if (e instanceof ApiException) {
            HttpLog.e("--> e instanceof ApiException err:" + e);
            onError((ApiException) e);
        } else {
            HttpLog.e("--> e !instanceof ApiException err:" + e);
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {
        HttpLog.e("-->http is onComplete");
    }

    /**
     * 请求开始
     */
    public void onStart() {

    }

    /**
     * 请求成功
     *
     * @param t 数据
     */
    public abstract void onSuccess(T t);

    /**
     * 请求异常
     *
     * @param e 异常类型
     */
    public abstract void onError(ApiException e);
}
