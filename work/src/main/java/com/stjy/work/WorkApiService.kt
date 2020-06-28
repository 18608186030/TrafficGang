package com.stjy.work

import com.jiuwe.common.net.resp.BaseRespData
import com.stjy.baselib.net.net2.api.BaseApi
import com.stjy.work.bean.ZiXunAllResp
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *公共api
 */
interface WorkApiService {
    @POST(BaseApi.AREA_ALL)
    fun getListData(@Query("page_size") page_size: Int, @Query("page_num") page_num: Int): Observable<BaseRespData<ZiXunAllResp>>
}