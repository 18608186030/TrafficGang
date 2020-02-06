package com.stjy.baselib.common;


import com.stjy.baselib.BuildConfig;

/**
 * 管理App常量
 */
public class AppConstants {

    /**
     * APP是否是开发或者模式 true 为开发或者测试环境，false 为正式环境
     */
    public static final boolean isDebug = BuildConfig.IS_DEBUG;
    /* SP key值 */
    public static final String KEY_TOKEN = "token";
    public static final String LAST_ACCOUNT = "last_account";
    public static final String USER_DATA = "user_data";
    public static final String NETWORK_TIPS_SWITCH = "network_tips_switch";
    public static final String CITY_ID = "cityId";
    public static final String CITY_NAME = "cityName";
    public static final String BDLOCATION_LIST = "bdlocation_list";
    public static final String DOWNLOAD_ADS_BEAN = "download_ads_bean";
    public static final String DOWNLOAD_INDEX = "download_index";
    public static final String DOWNLOAD_ADS_SPLASH_IMAGE = "splash_image";
    public static final String SPLASH_AD_TARGET_URL = "splash_ad_target_url";
    public static final String SPLASH_AD_TARGET_TITLE = "splash_ad_target_title";
    public static final String JPUSH_REGISTER_ID = "jpush_register_id";
    public static final String KEY_IS_INSTALL_APK = "is_install_apk";

    /* SP 文件名 */
    /**
     * 用户数据保存
     */
    public static final String APP_SP = "app_sp";
    /**
     * 用户数据保存
     */
    public static final String USER_JSON = "user_json";
    /**
     * 视频播放规则配置
     */
    public static final String PLAY_CONFIG = "play_config";
    /**
     * 视频播放进度记录
     */
    public static final String PLAY_POSITION = "study_video_position";
    /**
     * 极光IM用户名和密码
     */
    public static final String JMESSAGE_USER_JSON = "jmessage_user_json";
    /**
     * H5链接保存
     */
    public static final String H5_CONFIG = "h5_config";
    /**
     * APP更新
     */
    public static final String APP_UPDATE = "app_update";
    /**
     * 练习题当前位置
     */
    public static final String QUES_OFFSET = "ques_offset";
    public static final String COURSE_EXERCISE = "course_exercise";
    /* SP 文件名 */
    // 热点视频分享链接
    public static final String HOT_SPOT_VIDEO_SHARE_URL = isDebug ? "http://192.168.8.68:8082/#/?vid=" : "http://app.staq360.com/static/h5/videoh5/#/?vid=";
    // 保险
    public static final String INSURE = "http://sc.staq360.com/h5/insure?navigationHidden&time=" + System.currentTimeMillis();
    // 车友贷
    public static final String LOAN = "http://sc.staq360.com/h5/loan?navigationHidden&time=" + System.currentTimeMillis();
    // 继续教育复训练习
    public static final String FU_XUN_LIAN_XI = "http://wechat.jsy360.cn/gewechat/recovery/index";

    public static final int PAGE_SIZE = 20;

    public static String WX_APP_ID = "";
    public static String WX_APP_APPSECRET = "";
    public static String QQ_APP_ID = "";
    public static String QQ_APP_KEY = "";
    public static String BUGLY_APP_ID = "";
    public static String UMENG_APP_KEY = "";
}
