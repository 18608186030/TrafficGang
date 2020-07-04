package com.stjy.baselib.base

import android.app.Application
import android.content.Context

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: 程序进程
 */
class BaseApplication : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL,
        "com.stjy.baselib.base.BaseApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false) {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var context: Context
        lateinit var application: Application
            private set
    }
}
