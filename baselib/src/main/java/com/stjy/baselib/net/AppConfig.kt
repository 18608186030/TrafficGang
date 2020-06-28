package com.stjy.baselib.net

import com.stjy.baselib.BuildConfig

/**
 * @author daifalin
 * @date 2018/11/21 9:33 AM
 * @ClassName StudyApiService
 * @Description
 */
interface  AppConfig {
    companion object {
        /**
         * APP是否是开发或者模式 true 为开发或者测试环境，false 为正式环境
         */
        const val isDebug = BuildConfig.IS_DEBUG
        const val KEY_TOKEN = "token"
        const val USER_DATA = "user_data"
        /**
         * 用户数据保存
         */
        const val USER_JSON = "user_json"

        const val USER_ADMIN = "admin/"

        /**
         * 测试BaseUrl
         */
        private const val TEST_URL_254 = "http://cjs-pro-api.zqf.com.cn:80/"

        /**
         * 正式BaseUrl
         */
        private const val RELEASE_URL = "https://cjs-pro-api.cjs.com.cn/"

        /**
         * 域名地址
         */
        val BASE_URL = if (isDebug) TEST_URL_254 else RELEASE_URL

        /**
         * OSS访问的endpoint地址
         */
        const val OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com"

        /**
         * OSS STS Server 地址
         */
        const val STS_SERVER_URL = "http://app.staq360.com/admin/education/courseware/public/oss/sts/credentials"

        /**
         * 获取OSS文件访问URL
         */
        const val OSS_FILE_URL = USER_ADMIN + "education/courseware/public/oss/url"
    }
}