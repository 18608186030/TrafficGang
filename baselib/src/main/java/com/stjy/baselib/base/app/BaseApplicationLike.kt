package com.stjy.baselib.base.app

import android.annotation.TargetApi
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.stjy.baselib.R
import com.stjy.baselib.net.AppConfig
import com.stjy.baselib.net.net1.interceptor.TokenInterceptor
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.upgrade.UpgradeListener
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.entry.DefaultApplicationLike
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpHeaders
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.stjy.baselib.ui.activity.UpgradeActivity
import com.stjy.baselib.bean.model.LoginUser
import com.stjy.baselib.utils.OSSUploadUtils
import com.stjy.baselib.wigiet.refreshheader.material.MaterialHeader
import me.jessyan.autosize.AutoSizeConfig
import me.yokeyword.fragmentation.Fragmentation
/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: 这是application的代理类（用于初始化一些东西）
 */
class BaseApplicationLike(application: Application?, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean,
                          applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent?)
    : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag,
        applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    override fun onCreate() {
        super.onCreate()
        initBugly()
        initARouter()
        initNet()
        initSmartRefreshLayout()
        initOther()
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        if (!AppConfig.isDebug) {
            Beta.installTinker(this)
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: ActivityLifecycleCallbacks?) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    private fun initBugly() {
        if (!AppConfig.isDebug) {
            Bugly.init(application, "e0f8767b25", AppConfig.isDebug, with(CrashReport.UserStrategy(application)) {
                // 设置是否为上报进程
                val packageName = application.packageName// 获取当前包名
                val processName = ProcessUtils.getCurrentProcessName()// 获取当前进程名
                this.isUploadProcess = processName == null || processName == packageName

                // 自动初始化开关
                Beta.autoInit = true
                // 延迟初始化
                Beta.initDelay = 2 * 1000.toLong()
                // 自动检查更新开关
                Beta.autoCheckUpgrade = true
                // 升级检查周期设置
                Beta.upgradeCheckPeriod = 60 * 1000.toLong()
                // 设置更新弹窗默认展示的banner
                Beta.defaultBannerId = R.mipmap.update
                // 设置开启显示打断策略，设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
                Beta.showInterruptedStrategy = true
                // 设置Wifi下自动下载
                Beta.autoDownloadOnWifi = true
                // 设置自定义升级对话框UI布局
                Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
                // 设置自定义tip弹窗UI布局
                Beta.tipsDialogLayoutId = R.layout.tips_dialog
                // 设置是否显示消息通知
                Beta.enableNotification = false
                // 设置是否显示弹窗中的apk信息
                Beta.canShowApkInfo = false
                // 设置是否开启热更新能力，默认为true
                Beta.enableHotfix = true
                // 设置是否自动下载补丁，默认为true
                Beta.canAutoDownloadPatch = true
                // 设置是否自动合成补丁，默认为true
                Beta.canAutoPatch = true
                // 设置是否提示用户重启，默认为false
                Beta.canNotifyUserRestart = false
                // 设置渠道信息
                //Bugly.setAppChannel(this@BaseApplication, ChannelReaderUtil.getChannel(com.stjy.baselib.base.mvvm.getApplication()))
                // 更新监听，收到策略时回调
                Beta.upgradeListener = UpgradeListener { ret: Int, upgradeInfo: UpgradeInfo?, isManual: Boolean, isSilence: Boolean ->
                    if (upgradeInfo != null) {
                        //跳转更新Activity
                        val intent = Intent()
                        intent.setClass(application, UpgradeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        application.startActivity(intent)
                    }
                }
                this
            })
        }
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
        ARouter.init(application) // 尽可能早，推荐在Application中初始化
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
        LogUtils.getConfig()
                .setLogSwitch(AppConfig.isDebug)
                .setConsoleSwitch(true)
                .setBorderSwitch(false).globalTag = "TrafficGang"
        //初始化阿里云OSS上传
        OSSUploadUtils.init(application)
        //Fragmentation 初始化
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(AppConfig.isDebug)
                .install()
        //屏幕适配
        AutoSizeConfig.getInstance().isExcludeFontScale = true
        //AutoSizeConfig.getInstance()
        //        .unitsManager
        //        .setSupportDP(true)
        //        .setSupportSP(true)
        //        .supportSubunits = Subunits.MM
        LiveEventBus.get()
                .config()
                .supportBroadcast(application)
                .lifecycleObserverAlwaysActive(true)
    }
}