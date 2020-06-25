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
    public static final String USER_DATA = "user_data";
    public static final String CITY_ID = "cityId";
    public static final String CITY_NAME = "cityName";
    /**
     * 用户数据保存
     */
    public static final String USER_JSON = "user_json";
    /**
     * 视频播放进度记录
     */
    public static final String PLAY_POSITION = "study_video_position";
    /**
     * 练习题当前位置
     */
    public static final String QUES_OFFSET = "ques_offset";
    public static final String COURSE_EXERCISE = "course_exercise";

}
