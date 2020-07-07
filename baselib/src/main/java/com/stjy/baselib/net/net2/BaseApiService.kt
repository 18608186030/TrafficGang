package com.stjy.baselib.net.net2

import com.stjy.baselib.bean.model.ZIXunAllListResp
import com.stjy.baselib.net.BaseApi
import io.reactivex.Observable
import com.stjy.baselib.net.net2.resp.BaseRespData
import retrofit2.http.GET

import retrofit2.http.Query

/**
 * @Author: superman
 * @CreateTime: 2020/7/7
 * @Describe:
 */
interface BaseApiService {
    @GET(BaseApi.INFORMATION)
    fun getListData(@Query("page_size") page_size: Int, @Query("page_num") page_num: Int): Observable<BaseRespData<ZIXunAllListResp>>
}