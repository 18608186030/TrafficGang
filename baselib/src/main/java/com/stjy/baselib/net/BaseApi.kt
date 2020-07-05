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
         * 获取广告位置图片
         */
        var ADVERT_LIST: String ="admin/cms/news/public/advert/list/%s"

        /**
         * 根据字典类型获取字典
         */
        var DICT: String = "admin/public/sys/dict/type/%s"
    }
}