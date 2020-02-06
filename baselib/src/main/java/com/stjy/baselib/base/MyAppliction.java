package com.stjy.baselib.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.stjy.baselib.R;
import com.stjy.baselib.common.AppConstants;
import com.stjy.baselib.common.LoginUser;
import com.stjy.baselib.net.HttpConstant;
import com.stjy.baselib.net.interceptor.TokenInterceptor;
import com.stjy.baselib.utils.OSSUploadUtils;
import com.stjy.baselib.utils.VODUploadUtils;
import com.stjy.baselib.wigiet.refreshheader.material.MaterialHeader;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.model.HttpHeaders;

import me.jessyan.autosize.AutoSizeConfig;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by superman on 2018/4/9.
 */
/**
 * Created by superman on 2018/4/9.
 */
public class MyAppliction extends Application {
    public static Context context;
    private static Application sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
        context = getApplicationContext();
        MultiDex.install(this);
        initARouter();
        initLog();
        initNet();
        initSmartRefreshLayout();
        initOther();
    }

    private void initLog() {
        LogUtils.getConfig()
                .setLogSwitch(AppConstants.isDebug)
                .setConsoleSwitch(true)
                .setBorderSwitch(false)
                .setGlobalTag("TrafficGang");
    }

    public static Application getApplication() {
        return sApplication;
    }

    /**
     * 初始化网络请求
     */
    private void initNet() {
        EasyHttp.init(getApplication());
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = LoginUser.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            httpHeaders.put("token", token);
        }
        EasyHttp.getInstance()
                .debug(" http_log", AppConstants.isDebug)
                //设置全局URL
                .setBaseUrl(HttpConstant.BASE_URL)
                //设置全局公共头
                .addCommonHeaders(httpHeaders)
                //设置超时时间
                .setReadTimeOut(30000)
                .setWriteTimeOut(30000)
                .setConnectTimeout(30000)
                .setRetryCount(0)
                //设置超时重试间隔时间
                .setRetryDelay(500)
                .addInterceptor(new TokenInterceptor());
    }

    private void initARouter() {
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        if (AppConstants.isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

    private void initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setDisableContentWhenRefresh(true)
                    .setPrimaryColorsId(R.color.white);
            return new MaterialHeader(context)
                    .setColorSchemeResources(R.color.colorAccent);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            layout.setDisableContentWhenLoading(true);
            return new ClassicsFooter(context)
                    .setPrimaryColorId(R.color.white)
                    .setFinishDuration(0);
        });
    }

    private void initOther() {
        //初始化阿里云OSS上传
        OSSUploadUtils.init(this);
        VODUploadUtils.init(this);

        //Fragmentation 初始化
        Fragmentation.builder()
                .stackViewMode(Fragmentation.NONE)
                .debug(AppConstants.isDebug)
                .install();
        //屏幕适配
        AutoSizeConfig.getInstance().setExcludeFontScale(true);
        LiveEventBus.get()
                .config()
                .supportBroadcast(getApplication())
                .lifecycleObserverAlwaysActive(true);
    }

}
