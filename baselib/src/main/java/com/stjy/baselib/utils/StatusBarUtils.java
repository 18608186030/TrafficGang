package com.stjy.baselib.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;

/**
 * @author daifalin
 * @date 2019/2/21 10:27 AM
 * @ClassName StatusBarUtils
 * @Description 状态栏工具
 */
public class StatusBarUtils {
    /**
     * Set the status bar's color.
     * default color
     *
     * @param activity The activity.
     */
    public static void setStatusBarColor(@NonNull final Activity activity) {
        int color = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 0xffffffff : 0xff80ffff;
        BarUtils.setStatusBarColor(activity, color);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        BarUtils.setStatusBarColor(activity, color);
    }

    /**
     * Set the status bar's color.
     * default color
     *
     * @param activity The activity.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, final boolean isDecor) {
        int color = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 0xffffffff : 0xff80ffff;
        BarUtils.setStatusBarColor(activity, color, isDecor);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color, final boolean isDecor) {
        BarUtils.setStatusBarColor(activity, color, isDecor);
    }

    /**
     * Set the status bar's color.
     * default color
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar) {
        int color = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 0xffffffff : 0xff80ffff;
        BarUtils.setStatusBarColor(fakeStatusBar, color);
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar, @ColorInt final int color) {
        BarUtils.setStatusBarColor(fakeStatusBar, color);
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        BarUtils.addMarginTopEqualStatusBarHeight(view);
    }
}
