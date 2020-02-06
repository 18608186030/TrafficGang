package com.stjy.baselib.utils

/**
 * The interface A router hub.
 *
 * @author acb
 * @date 2018 /6/28 10:04
 * @ClassName ARouterHub
 * @Description <ARouter跳转路由地址>
</ARouter跳转路由地址> */
interface ARouterHub {
    companion object {

        /**
         * The constant APP.
         * 宿主APP组件
         */
        const val APP = "/app/"

        /**
         * 主界面
         */
        const val APP_MainActivity = APP + "MainActivity"
    }
}
