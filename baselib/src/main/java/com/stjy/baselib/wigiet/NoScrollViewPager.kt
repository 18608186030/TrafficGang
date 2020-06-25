package com.stjy.baselib.wigiet

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 自定义一个不能左右滑动的ViewPager
 * @author Beyond
 */
class NoScrollViewPager : ViewPager {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    override fun onTouchEvent(arg0: MotionEvent): Boolean =false

    /**
     * 不拦截事件
     */
    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean =false

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean =super.dispatchTouchEvent(ev)
}
