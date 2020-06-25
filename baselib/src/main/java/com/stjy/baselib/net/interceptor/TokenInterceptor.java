package com.stjy.baselib.net.interceptor;

import com.blankj.utilcode.util.GsonUtils;
import com.stjy.baselib.event.LoginEvent;
import com.stjy.baselib.net.HttpParamContact;
import com.stjy.baselib.net.request.BaseApiResult;
import com.stjy.baselib.utils.EventBusUtils;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author acb
 * @date 2018/6/11 15:08
 * @ClassName TokenInterceptor
 * @Description <Token拦截器>
 */
public class TokenInterceptor extends BaseExpiredInterceptor {


    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        BaseApiResult apiResult = GsonUtils.fromJson(bodyString, BaseApiResult.class);
        if (apiResult != null) {
            String code = apiResult.getCode();
            switch (code) {
                case HttpParamContact.Code.REFRESH_TOKEN_EXPIRED:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public Response responseExpired(Chain chain, String bodyString) {
        BaseApiResult apiResult = GsonUtils.fromJson(bodyString, BaseApiResult.class);
        Response response = null;
        try {
            Request request = chain.request();
            response = chain.proceed(request);
            switch (apiResult.getCode()) {
                case HttpParamContact.Code.REFRESH_TOKEN_EXPIRED:
                    refreshLogin();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void refreshLogin() {
        EventBusUtils.post(new LoginEvent());
    }
}
