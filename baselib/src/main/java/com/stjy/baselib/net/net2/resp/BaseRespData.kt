package com.stjy.baselib.net.net2.resp

import com.google.gson.annotations.SerializedName
import com.jiuwe.common.net.resp.BaseResp
import java.io.Serializable

open class BaseRespData<T>(
        @SerializedName("data")
        var data: T
) : BaseResp(),Serializable