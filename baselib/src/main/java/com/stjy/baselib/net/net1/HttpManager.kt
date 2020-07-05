package com.stjy.baselib.net.net1

import com.stjy.baselib.net.net1.request.BaseDeleteRequest
import com.stjy.baselib.net.net1.request.BaseGetRequest
import com.stjy.baselib.net.net1.request.BasePostRequest
import com.stjy.baselib.net.net1.request.BasePutRequest
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.request.DownloadRequest

/**
 * http请求管理类
 *
 * @author acb
 */
object HttpManager {
    /**
     * get请求
     *
     * @param url 请求地址
     */
    @JvmStatic
    fun get(url: String?): BaseGetRequest {
        return BaseGetRequest(url)
    }

    /**
     * post请求
     *
     * @param url 请求地址
     */
    @JvmStatic
    fun post(url: String?): BasePostRequest {
        return BasePostRequest(url)
    }

    /**
     * put请求
     *
     * @param url 请求地址
     */
    @JvmStatic
    fun put(url: String?): BasePutRequest {
        return BasePutRequest(url)
    }

    /**
     * delete请求
     *
     * @param url 请求地址
     */
    @JvmStatic
    fun delete(url: String?): BaseDeleteRequest {
        return BaseDeleteRequest(url)
    }

    /**
     * downLoad文件请求
     *
     * @param url 请求地址
     */
    @JvmStatic
    fun downLoad(url: String?): DownloadRequest {
        return EasyHttp.downLoad(url)
    }
}