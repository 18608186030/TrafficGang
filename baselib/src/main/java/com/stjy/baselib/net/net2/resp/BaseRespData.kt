package com.jiuwe.common.net.resp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseRespData<T>(
        @SerializedName("data")
        var data: T
) : BaseResp(),Serializable