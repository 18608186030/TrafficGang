package com.stjy.baselib.net2.resp

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class BaseRespListData<T>(
        @SerializedName("data")
        var data: ArrayList<T>
) : Serializable