package com.stjy.baselib;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

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
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        if (BuildConfig.IS_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

    public static Application getApplication() {
        return sApplication;
    }
}
