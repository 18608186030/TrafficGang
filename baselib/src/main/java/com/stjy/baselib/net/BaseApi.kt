package com.stjy.baselib.net

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
        const val AREA_ALL = "api/v2/information"
        /**
         * 根据字典类型获取字典
         */
        const val INFORMATION: String = "api/v2/information"
    }
}