package com.stjy.baselib.net.net1.request

import com.zhouyou.http.model.ApiResult

/**
 * @author acb
 */
class BaseApiResult<T> : ApiResult<T>() {
    override fun isOk(): Boolean {
        return code==0
    }
}