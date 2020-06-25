package com.stjy.baselib.utils

import android.app.Activity
import android.os.Build
import android.support.annotation.ColorInt
import android.view.View
import com.blankj.utilcode.util.BarUtils

/**
 * @author daifalin
 * @date 2019/2/21 10:27 AM
 * @ClassName StatusBarUtils
 * @Description 状态栏工具
 */
object StatusBarUtils {
    /**
     * Set the status bar's color.
     * default color
     *
     * @param activity The activity.
     */
    fun setStatusBarColor(activity: Activity) {
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -0x1 else -0x7f0001
        BarUtils.setStatusBarColor(activity, color)
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     */
    fun setStatusBarColor(activity: Activity, @ColorInt color: Int) {
        BarUtils.setStatusBarColor(activity, color)
    }

    /**
     * Set the status bar's color.
     * default color
     *
     * @param activity The activity.
     * @param isDecor  True to add fake status bar in DecorView,
     * false to add fake status bar in ContentView.
     */
    fun setStatusBarColor(activity: Activity, isDecor: Boolean) {
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -0x1 else -0x7f0001
        BarUtils.setStatusBarColor(activity, color, isDecor)
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param isDecor  True to add fake status bar in DecorView,
     * false to add fake status bar in ContentView.
     */
    fun setStatusBarColor(activity: Activity, @ColorInt color: Int, isDecor: Boolean) {
        BarUtils.setStatusBarColor(activity, color, isDecor)
    }

    /**
     * Set the status bar's color.
     * default color
     *
     * @param fakeStatusBar The fake status bar view.
     */
    fun setStatusBarColor(fakeStatusBar: View) {
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -0x1 else -0x7f0001
        BarUtils.setStatusBarColor(fakeStatusBar, color)
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     */
    fun setStatusBarColor(fakeStatusBar: View, @ColorInt color: Int) {
        BarUtils.setStatusBarColor(fakeStatusBar, color)
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    fun addMarginTopEqualStatusBarHeight(view: View) {
        BarUtils.addMarginTopEqualStatusBarHeight(view)
    }
}