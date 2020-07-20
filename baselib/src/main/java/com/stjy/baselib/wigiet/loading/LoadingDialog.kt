package com.stjy.baselib.wigiet.loading

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.stjy.baselib.base.mvc.BaseActivity

/**
 * @Author: superman
 * @CreateTime: 2020/7/17
 * @Describe:
 */
class LoadingDialog(private val mContext: Context, dialogSize: Int = 90) : Dialog(mContext) {

    private var llContext: LinearLayout
    private var loadingDialog: LoadingView
    private var tvContext: TextView

    init {
        llContext = LinearLayout(mContext)
        llContext.orientation = LinearLayout.VERTICAL
        llContext.setPadding(SizeUtils.dp2px(20.toFloat()),
                SizeUtils.dp2px(20.toFloat()),
                SizeUtils.dp2px(20.toFloat()),
                SizeUtils.dp2px(20.toFloat()))
        llContext.layoutParams = with(LinearLayout.LayoutParams(SizeUtils.dp2px(dialogSize.toFloat()), SizeUtils.dp2px(dialogSize.toFloat()))) {
            this.gravity = Gravity.CENTER
            this
        }

        //菊花进度动画
        loadingDialog = LoadingView(mContext, 20, Color.WHITE)
        llContext.addView(loadingDialog, with(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)) {
            this.gravity = Gravity.CENTER
            this
        })
        //文本内容
        tvContext = TextView(mContext)
        llContext.addView(tvContext, with(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)) {
            this.gravity = Gravity.CENTER
            this.topMargin = SizeUtils.dp2px(15.toFloat())
            this
        })
        this.setContentView(llContext)
        //this.setContentView(R.layout.layout_loadingdialog)
    }

    /**
     * 弹出自定义LoadingDialog
     *
     * @param msg        提示文本
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return 自定义的LoadingDialog
     */
    @SuppressLint("WrongConstant", "ResourceAsColor")
    fun showing(content: CharSequence? = null,
                contentTextColor: Int? = null,
                contentTextSize: Int? = null,
                cancelable: Boolean? = null,
                canceledOnTouchOutside: Boolean? = null,
                strokeWidth: Int? = null,
                strokeColor: Int? = null,
                solidColor: Int? = null,
                cancelListener: DialogInterface.OnCancelListener? = null,
                cornerRadius: Int? = null,
                LoadingViewSize: Int? = null,
                dimAmount: Float? = null) {
        if (!(mContext as BaseActivity).isFinishing) {
            this.llContext.background = with(GradientDrawable()) {
                this.shape = GradientDrawable.RECTANGLE
                this.gradientType = GradientDrawable.RECTANGLE
                this.setStroke(strokeWidth ?: 0, strokeColor ?: Color.parseColor("#99000000"))
                this.setColor(solidColor ?: Color.parseColor("#99000000"))
                this.cornerRadii = floatArrayOf(SizeUtils.dp2px((cornerRadius
                        ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat(),
                        SizeUtils.dp2px((cornerRadius ?: 15).toFloat()).toFloat())
                this
            }
            with(this.loadingDialog) {
                this.setSize(LoadingViewSize ?: 25)
            }
            with(this.tvContext) {
                this.text = content ?: "加载中..."
                this.setTextColor(contentTextColor ?: Color.parseColor("#FFFFFFFF"))
                this.textSize = (contentTextSize ?: 14).toFloat()
            }
            this.setCancelable(cancelable ?: false)// 按返回键是否取消
            this.setCanceledOnTouchOutside(canceledOnTouchOutside ?: false)// 点击外部是否取消
            this.setOnCancelListener(cancelListener)// 监听返回键处理
            this.window.setBackgroundDrawable(with(GradientDrawable()) {
                this.setColor(0)
                this
            })
            this.window.attributes = with(this.window.attributes) {
                if (dimAmount?:0.2f in 0.0..1.0) {
                    this.dimAmount = dimAmount?:0.2f // 设置背景层透明度
                }
                this.gravity = Gravity.CENTER // 设置居中
                this
            }
            if (!this.isShowing)
                this.show()
        }
    }

    /**
     * 关闭自定义LoadingDialog
     */
    fun dismissing() {
        if (!(mContext as BaseActivity).isFinishing) {
            if (this.isShowing)
                this.dismiss()
        }
    }
}