package com.stjy.baselib.event

/**
 * 全局登陆事件
 * action=0 默认去登陆
 * action=0 去登陆 包括Token失效
 * action=1 退出登陆 此时因该清楚用户数据
 */
open class LoginEvent(val action: Int? = null) {
    companion object {
        const val GOLOGIN = 0
        const val LOGINOUT = 1
        const val LOGINOK = -1
    }
}
