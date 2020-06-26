package com.stjy.baselib.net;


import com.stjy.baselib.common.AppConstants;

/**
 * @author daifalin
 * @date 2018/11/21 9:33 AM
 * @ClassName StudyApiService
 * @Description
 */
public class HttpConstant {

    private static boolean isDebug = AppConstants.isDebug;

    public static final String USER_ADMIN = "admin/";

    /**
     * 测试BaseUrl
     */
    private static final String TEST_URL_254 = "http://182.150.22.222:6061";

    /**
     * 正式BaseUrl
     */
    private static final String RELEASE_URL = "http://es.staq360.com/";

    /**
     * 域名地址
     */
    public static final String BASE_URL = isDebug ? TEST_URL_254 : RELEASE_URL;

    /**
     * OSS访问的endpoint地址
     */
    public static final String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    /**
     * OSS STS Server 地址
     */
    public static final String STS_SERVER_URL = "http://app.staq360.com/admin/education/courseware/public/oss/sts/credentials";

    /**
     * 获取OSS文件访问URL
     */
    public static final String OSS_FILE_URL = USER_ADMIN + "education/courseware/public/oss/url";

}
