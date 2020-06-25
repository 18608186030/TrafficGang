package com.stjy.baselib.common

import com.stjy.baselib.BuildConfig

/**
 * 管理App常量
 */
object AppConstants {
    /**
     * APP是否是开发或者模式 true 为开发或者测试环境，false 为正式环境
     */
    const val isDebug = BuildConfig.IS_DEBUG

    /* SP key值 */
    const val KEY_TOKEN = "token"
    const val USER_DATA = "user_data"
    /**
     * 用户数据保存
     */
    const val USER_JSON = "user_json"

    /**
     * 视频播放进度记录
     */
    const val PLAY_POSITION = "study_video_position"

    /**
     * 练习题当前位置
     */
    const val QUES_OFFSET = "ques_offset"
    const val COURSE_EXERCISE = "course_exercise"
}