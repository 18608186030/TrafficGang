package com.stjy.baselib.net.net2.api

import com.stjy.baselib.net.net2.resp.BaseRespListData
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *公共api
 */
interface BaseApiService {
    @POST(BaseApi.AREA_ALL)
    fun getAskList(@Body askPageReq: Any): Observable<BaseRespListData<Any>>
}