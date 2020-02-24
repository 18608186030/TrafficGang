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
        //********************************app模块***************************************************
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
         * The constant APP.
         * 宿主APP组件
         */
        private const val LOGIN = "/login/"

        //********************************工作模块***************************************************
        /**
         * 主界面
         */
        const val LOGIN_ACTIVITY = LOGIN + "LoginActivity"
        /**
         * 工作模块界面
         */
        const val LOGIN_FRAGMENT = LOGIN + "LoginFragment"

        /**
         * 找回密码第一级界面
         */
        const val LOGIN_FINDPASSWORDFRIST_FRAGMENT = LOGIN + "FindPasswordFristFragment"

        /**
         * 找回密码第二级界面
         */
        const val LOGIN_FINDPASSWORDSECOND_FRAGMENT = LOGIN + "FindPasswordSecondFragment"
        /**
         * 找回密码第三级界面
         */
        const val LOGIN_FINDPASSWORDThird_FRAGMENT = LOGIN + "FindPasswordThirdFragment"
        /**
         * 找回密码第三级界面
         */
        const val LOGIN_REGISTER_FRAGMENT = LOGIN + "RegisterFragment"

        /**
         * 注册企业
         */
        const val LOGIN_REGISTERENTERPRISE_FRAGMENT = LOGIN + "RegisterEnterpriseFragment"

        /**
         * 选择或创建企业界面
         */
        const val LOGIN_CHECKENTERPRISEMODE_FRAGMENT = LOGIN + "CheckEnterpriseModeFragment"

        /**
         * 选择或创建企业界面
         */
        const val LOGIN_SEARCHENTERPRISE_FRAGMENT = LOGIN + "SearchEnterpriseFragment"
        /**
         * 提交信息列表
         */
        const val LOGIN_SUBMITENTERPRISELIST_FRAGMENT = LOGIN + "SubmitEnterpriseListFragment"
        /**
         * 企业注册信息审核详情
         */
        const val LOGIN_AUDITDETAILS_FRAGMENT = LOGIN + "AuditDetailsFragment"

        //********************************工作模块模块***************************************************
        /**
         * 工作模块
         */
        private const val WORK = "/work/"

        /**
         * 工作模块界面
         */
        const val WORK_FRAGMENT = WORK + "WorkFragment"

        //********************************通讯录模块***************************************************
        /**
         * 通讯录模块
         */
        private const val MAILLIST = "/maillist/"

        /**
         * 通讯录模块界面
         */
        const val MAILLIST_FRAGMENT = MAILLIST + "MailListFragment"

        /**
         * 搜索通讯录二级界面
         */
        const val MAILLIST_SEARCHMAILLIST_ACTIVITY = MAILLIST + "SearchMailListActivity"

        //********************************个人中心模块***************************************************
        /**
         * 个人中心模块
         */
        private const val PERSON = "/person/"

        /**
         * 个人中心模块界面
         */
        const val PERSON_FRAGMENT = PERSON + "PersonFragment"

        /**
         * 我的档案主页
         */
        const val ARCHIVE_ACTIVITY = PERSON + "ArchiveActivity"

        /**
         * 我的档案主页
         */
        const val ARCHIVE_FRAGMENT = PERSON + "ArchiveFragment"

        /**
         * 个人信息界面
         */
        const val PERSON_INFO_FRAGMENT = PERSON + "PersonInfoFragment"
    }
}
