package com.stjy.baselib.net.net2.resp

import java.io.Serializable

open class BaseResp() : Serializable {
    var code: Int? = null
    var msg: String? = null
}
