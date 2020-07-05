package com.stjy.baselib.net.net1.interceptor

import com.blankj.utilcode.util.GsonUtils
import com.stjy.baselib.bean.event.LoginEvent
import com.stjy.baselib.net.net1.request.BaseApiResult
import com.stjy.baselib.utils.EventBusUtils.post
import com.zhouyou.http.interceptor.BaseExpiredInterceptor
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author acb
 * @date 2018/6/11 15:08
 * @ClassName TokenInterceptor
 * @Description <Token拦截器>
</Token拦截器> */
class TokenInterceptor : BaseExpiredInterceptor() {
    override fun isResponseExpired(response: Response, bodyString: String): Boolean {
        val apiResult = GsonUtils.fromJson(bodyString, BaseApiResult::class.java)
        if (apiResult != null) {
            when (apiResult.code) {
                -100 -> return true
                else -> {
                }
            }
        }
        return false
    }

    override fun responseExpired(chain: Interceptor.Chain, bodyString: String): Response {
        val apiResult = GsonUtils.fromJson(bodyString, BaseApiResult::class.java)
        var response: Response? = null
        try {
            val request = chain.request()
            response = chain.proceed(request)
            when (apiResult.code) {
                -100 -> refreshLogin()
                else -> {
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response!!
    }

    private fun refreshLogin() {
        post(LoginEvent(LoginEvent.GOLOGIN))
    }
}