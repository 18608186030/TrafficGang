/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stjy.baselib.wigiet.loading

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.SizeUtils
import com.stjy.baselib.R

/**
 * 用于显示 Loading 的 [View]，支持颜色和大小的设置。
 *
 * @author cginechen
 * @date 2016-09-21
 */
class LoadingView : View {
    private var mSize: Int
    private var mPaintColor: Int
    private var mAnimateValue = 0
    private var mAnimator: ValueAnimator? = null
    private var mPaint: Paint? = null

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.MyLoadingView, defStyleAttr, 0)
        mSize = array.getDimensionPixelSize(R.styleable.MyLoadingView_loading_view_size, SizeUtils.dp2px(20f))
        mPaintColor = array.getInt(R.styleable.MyLoadingView_android_color, Color.WHITE)
        array.recycle()
        initPaint()
    }

    constructor(context: Context?, size: Int, color: Int) : super(context) {
        mSize = SizeUtils.dp2px(size.toFloat())
        mPaintColor = color
        initPaint()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint?.color = mPaintColor
        mPaint?.isAntiAlias = true
        mPaint?.strokeCap = Paint.Cap.ROUND
    }

    fun setColor(color: Int) {
        mPaintColor = color
        mPaint?.color = color
        invalidate()
    }

    fun setSize(size: Int) {
        mSize = SizeUtils.dp2px(size.toFloat())
        requestLayout()
    }

    private val mUpdateListener = AnimatorUpdateListener { animation ->
        mAnimateValue = animation.animatedValue as Int
        invalidate()
    }

    fun start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1)
            mAnimator?.addUpdateListener(mUpdateListener)
            mAnimator?.duration = 600
            mAnimator?.repeatMode = ValueAnimator.RESTART
            mAnimator?.repeatCount = ValueAnimator.INFINITE
            mAnimator?.interpolator = LinearInterpolator()
            mAnimator?.start()
        } else {
            mAnimator?.let {
                if (!it.isStarted) {
                    it.start()
                }
            }
        }
    }

    fun stop() {
        mAnimator?.removeUpdateListener(mUpdateListener)
        mAnimator?.removeAllUpdateListeners()
        mAnimator?.cancel()
        mAnimator = null
    }

    private fun drawLoading(canvas: Canvas, rotateDegrees: Int) {
        val width = mSize / 12
        val height = mSize / 6
        mPaint?.strokeWidth = width.toFloat()
        canvas.rotate(rotateDegrees.toFloat(), mSize / 2.toFloat(), mSize / 2.toFloat())
        canvas.translate(mSize / 2.toFloat(), mSize / 2.toFloat())
        for (i in 0 until LINE_COUNT) {
            canvas.rotate(DEGREE_PER_LINE.toFloat())
            mPaint?.alpha = (255f * (i + 1) / LINE_COUNT).toInt()
            canvas.translate(0f, -mSize / 2 + width / 2.toFloat())
            canvas.drawLine(0f, 0f, 0f, height.toFloat(), mPaint)
            canvas.translate(0f, mSize / 2 - width / 2.toFloat())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mSize, mSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val saveCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE)
        canvas.restoreToCount(saveCount)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) start() else stop()
    }

    companion object {
        private const val LINE_COUNT = 12
        private const val DEGREE_PER_LINE = 360 / LINE_COUNT
    }
}