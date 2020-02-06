package com.stjy.baselib.net;


import com.stjy.baselib.net.request.BaseDeleteRequest;
import com.stjy.baselib.net.request.BaseGetRequest;
import com.stjy.baselib.net.request.BasePostRequest;
import com.stjy.baselib.net.request.BasePutRequest;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.DownloadRequest;

/**
 * http请求管理类
 *
 * @author acb
 */
public class HttpManager {

    /**
     * get请求
     *
     * @param url 请求地址
     */
    public static BaseGetRequest get(String url) {
        return new BaseGetRequest(url);
    }

    /**
     * post请求
     *
     * @param url 请求地址
     */
    public static BasePostRequest post(String url) {
        return new BasePostRequest(url);
    }

    /**
     * put请求
     *
     * @param url 请求地址
     */
    public static BasePutRequest put(String url) {
        return new BasePutRequest(url);
    }

    /**
     * delete请求
     *
     * @param url 请求地址
     */
    public static BaseDeleteRequest delete(String url) {
        return new BaseDeleteRequest(url);
    }

    /**
     * downLoad文件请求
     *
     * @param url 请求地址
     */
    public static DownloadRequest downLoad(String url) {
        return EasyHttp.downLoad(url);
    }
}
