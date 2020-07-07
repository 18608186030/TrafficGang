package com.stjy.baselib.ui.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.stjy.baselib.R
/**
 * @Author: superman
 * @CreateTime: 2020/7/6
 * @Describe:具有加载状态页的adapter 灵活的修改状态页面内容，图片 按钮样式（没有不传递参数，默认即可）
 *           空页页面：adapter.showEmptyStaueView("你没有学习的课程哦！",R.mipmap.yingyezhizhao)
 *           初始加载页面：adapter.showLoadingStateView()
 *           错误页面：adapter.showRetryStateView(e.message,R.mipmap.yingyezhizhao,"刷新", View.OnClickListener {
 *                      ToastUtils.showLong("刷新")
 *                   })
 *
 */
abstract class SuperBaseQuickAdapter<T, K : BaseViewHolder?> : BaseQuickAdapter<T, K> {

    private var emptyStateView: View? = null
    private var retryStateView: View? = null
    private var loadingStateView: View? = null

    constructor(@LayoutRes layoutResId: Int, data: List<T>?) : super(data) {}
    constructor(data: List<T>?) : super(0, data) {}
    constructor(@LayoutRes layoutResId: Int) : super(layoutResId, null) {}

    fun initStaueView(context: Context?, smartRefreshLayout: SmartRefreshLayout?) {
        emptyStateView = LayoutInflater.from(context).inflate(R.layout.stateview_empty, smartRefreshLayout, false)
        retryStateView = LayoutInflater.from(context).inflate(R.layout.stateview_retry, smartRefreshLayout, false)
        loadingStateView = LayoutInflater.from(context).inflate(R.layout.stateview_loading, smartRefreshLayout, false)
    }

    fun showEmptyStaueView(content: String? = null,
                           iccon: Int = R.mipmap.empty_data) {
        emptyStateView?.let {
            it.findViewById<ImageView>(R.id.iv_image).setImageResource(iccon)

            it.findViewById<TextView>(R.id.tv_title).text = if (content.isNullOrEmpty()) {
                "似乎出了点问题"
            } else {
                content
            }

            this.emptyView = emptyStateView
        }
    }

    fun showRetryStateView(content: String? = null,
                           iccon: Int = R.mipmap.empty_data,
                           retry: String? = null,
                           btnClick: View.OnClickListener? = null) {
        retryStateView?.let {
            it.findViewById<ImageView>(R.id.iv_image).setImageResource(iccon)
            it.findViewById<TextView>(R.id.tv_title).text = if (content.isNullOrEmpty()) {
                "似乎出了点问题"
            } else {
                content
            }

            with(it.findViewById<SuperTextView>(R.id.btn_retry)) {
                this.text = if (content.isNullOrEmpty()) {
                    "重新加载"
                } else {
                    retry
                }
                this.setOnClickListener(btnClick)
            }

            this.emptyView = retryStateView
        }
    }

    fun showLoadingStateView() {
        loadingStateView?.let {
            this.emptyView = loadingStateView
        }
    }
}