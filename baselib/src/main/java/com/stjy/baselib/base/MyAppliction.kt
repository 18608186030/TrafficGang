package com.stjy.baselib.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.stjy.baselib.R
import com.stjy.baselib.common.LoginUser
import com.stjy.baselib.net.AppConfig
import com.stjy.baselib.net.net1.interceptor.TokenInterceptor
import com.stjy.baselib.utils.OSSUploadUtils
import com.stjy.baselib.wigiet.refreshheader.material.MaterialHeader
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpHeaders
import me.jessyan.autosize.AutoSizeConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * Created by superman on 2018/4/9.
 */
/**
 * Created by superman on 2018/4/9.
 */
class MyAppliction : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        context = applicationContext
        MultiDex.install(this)
        initARouter()
        initLog()
        initNet()
        initSmartRefreshLayout()
        initOther()
    }

    private fun initLog() {
        LogUtils.getConfig()
                .setLogSwitch(AppConfig.isDebug)
                .setConsoleSwitch(true)
                .setBorderSwitch(false).globalTag = "TrafficGang"
    }

    /**
     * 初始化网络请求
     */
    private fun initNet() {
        EasyHttp.init(application)
        val httpHeaders = HttpHeaders()
        val token = LoginUser.getInstance().token
        if (!TextUtils.isEmpty(token)) {
            httpHeaders.put("token", token)
        }
        EasyHttp.getInstance()
                .debug(" http_log", AppConfig.isDebug)
                //设置全局URL
                .setBaseUrl(AppConfig.BASE_URL)
                //设置全局公共头
                .addCommonHeaders(httpHeaders)
                //设置超时时间
                .setReadTimeOut(30000)
                .setWriteTimeOut(30000)
                .setConnectTimeout(30000)
                .setRetryCount(0)
                //设置超时重试间隔时间
                .setRetryDelay(500)
                .addInterceptor(TokenInterceptor())
    }

    private fun initARouter() {
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        if (AppConfig.isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

    private fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setDisableContentWhenRefresh(true)
                    .setPrimaryColorsId(R.color.white)
            MaterialHeader(context)
                    .setColorSchemeResources(R.color.colorAccent)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setDisableContentWhenLoading(true)
            ClassicsFooter(context)
                    .setPrimaryColorId(R.color.white)
                    .setFinishDuration(0)
        }
    }

    private fun initOther() {
        //初始化阿里云OSS上传
        OSSUploadUtils.init(this)

        //Fragmentation 初始化
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(AppConfig.isDebug)
                .install()
        //屏幕适配
        AutoSizeConfig.getInstance().isExcludeFontScale = true
//        AutoSizeConfig.getInstance()
//                .unitsManager
//                .setSupportDP(true)
//                .setSupportSP(true)
//                .supportSubunits = Subunits.MM
        LiveEventBus.get()
                .config()
                .supportBroadcast(application)
                .lifecycleObserverAlwaysActive(true)
    }

    companion object {
        lateinit var context: Context
        lateinit var application: Application
            private set
    }
}
