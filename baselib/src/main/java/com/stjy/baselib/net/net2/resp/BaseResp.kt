package com.stjy.baselib.net.net2.resp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResp() : Serializable {
    var code: Int? = null
    var msg: String? = null
}
