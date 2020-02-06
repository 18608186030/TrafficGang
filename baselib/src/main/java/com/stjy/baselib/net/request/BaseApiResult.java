package com.stjy.baselib.net.request;

import com.google.gson.annotations.SerializedName;
import com.zhouyou.http.model.ApiResult;

/**
 * @author acb
 */
public class BaseApiResult<T> extends ApiResult<T> {

    @SerializedName("success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean isOk() {
        return "200".equals(getCode());
    }

}
