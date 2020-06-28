package com.jiuwe.common.net.resp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResp() : Serializable {
    @SerializedName("code")
    var code: Int? = null

    @SerializedName("msg")
    var msg: String? = null
}
