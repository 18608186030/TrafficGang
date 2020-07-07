package com.stjy.baselib.net.net2.resp

import java.io.Serializable

open class BaseRespData<T>(
        var data: T
) : BaseResp(),Serializable