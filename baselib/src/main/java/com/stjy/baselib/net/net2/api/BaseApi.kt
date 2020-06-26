package com.stjy.baselib.net.net2.api

import com.stjy.baselib.net.AppConfig.Companion.USER_ADMIN

/**
 * 公共的接口
 *
 * @author daifalin
 */
interface BaseApi {
    companion object {
        /**
         * 全部地区数据
         */
        const val AREA_ALL = USER_ADMIN + "public/sys/area/all"
    }
}