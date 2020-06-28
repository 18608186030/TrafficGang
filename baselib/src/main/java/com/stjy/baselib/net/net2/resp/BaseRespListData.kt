package com.stjy.baselib.net.net2.resp

import com.google.gson.annotations.SerializedName
import com.jiuwe.common.net.resp.BaseResp
import java.io.Serializable
import java.util.*

class BaseRespListData<T>(
        @SerializedName("data")
        var data: ArrayList<T>
) : BaseResp(), Serializable