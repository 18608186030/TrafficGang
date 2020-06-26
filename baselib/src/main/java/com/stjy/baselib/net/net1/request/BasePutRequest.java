package com.stjy.baselib.net.net1.request;

import com.google.gson.reflect.TypeToken;
import com.stjy.baselib.net.net1.func.BaseApiResultFunc;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.func.CacheResultFunc;
import com.zhouyou.http.func.RetryExceptionFunc;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.request.BaseBodyRequest;
import com.zhouyou.http.subsciber.CallBackSubsciber;
import com.zhouyou.http.utils.RxUtil;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author acb
 */
public class BasePutRequest extends BaseBodyRequest<BasePutRequest> {

    public BasePutRequest(String url) {
        super(url);
    }

    public <T> Observable<T> execute(Type type) {
        return execute(new CallClazzProxy<BaseApiResult<T>, T>(type) {
        });
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return execute(new CallClazzProxy<BaseApiResult<T>, T>(clazz) {
        });
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<BaseApiResult<T>, T>(callBack) {
        });
    }


    @SuppressWarnings(value={"unchecked", "deprecation"})
    public <T> Observable<T> execute(CallClazzProxy<? extends ApiResult<T>, T> proxy) {
        return build().generateRequest()
                .map(new BaseApiResultFunc(proxy.getType()))
                .compose(isSyncRequest ? RxUtil._main() : RxUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(@NonNull Observable upstream) {
                        return upstream.map(new CacheResultFunc<T>());
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            }).subscribeWith(new CallBackSubsciber<T>(context, proxy.getCallBack()));
        } else {
            return observable.subscribeWith(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new BaseApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxUtil._main() : RxUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (this.requestBody != null) { //自定义的请求体
            return apiManager.putBody(url, this.requestBody);
        } else if (this.json != null) {//Json
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
            return apiManager.putJson(url, body);
        }  else if (this.object != null) {//自定义的请求object
            return apiManager.putBody(url, object);
        } else if (this.string != null) {//文本内容
            RequestBody body = RequestBody.create(mediaType, this.string);
            return apiManager.putBody(url, body);
        } else {
            return apiManager.put(url, params.urlParamsMap);
        }
    }
}
