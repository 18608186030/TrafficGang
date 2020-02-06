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
        private const val APP = "/app/"

        /**
         * 主界面
         */
        const val APP_MAIN_ACTIVITY = APP + "MainActivity"


        /**
         * 工作模块
         */
        private const val WORK = "/work/"

        /**
         * 工作模块界面
         */
        const val WORK_FRAGMENT = WORK + "WorkFragment"

        /**
         * 通讯录模块
         */
        private const val MAILLIST = "/maillist/"

        /**
         * 工作模块界面
         */
        const val MAILLIST_FRAGMENT = MAILLIST + "MailListFragment"
    }
}
