package com.stjy.baselib.ui.activity

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.R
import com.stjy.baselib.base.mvvm.BaseActivity
import com.stjy.baselib.ui.fragment.WebViewFragment
import com.stjy.baselib.utils.ARouterHub


/**
 * @author acb
 * @date 2018/6/8 15:18
 * @ClassName StandardWebActivity.java
 * @Description <web页面>
 */
@Route(path = ARouterHub.BASELIB_WEBVIEW_ACTIVITY, name = "Web页面")
class WebViewActivity : BaseActivity() {
    @Autowired(name = URL, desc = "链接")
    lateinit var webUrl: String

    @Autowired(name = TITILE, desc = "标题")
    lateinit var title: String

    @JvmField
    @Autowired(name = SHOWTOOBAR, desc = "是否显示标题栏")
    var showToobar: Boolean = true

    @JvmField
    @Autowired(name = SHOWSHARE, desc = "是否显示分享")
    var showShare: Boolean = true

    @JvmField
    @Autowired(name = SHOWWEBURLTITLE, desc = "显示web的title或显示自定义title")
    var showWebUrlTitle: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
    }

    override fun initListener() {

    }

    override fun initView() {
        loadRootFragment(R.id.fl_web, WebViewFragment.newInstance(url = webUrl, title = title, showToobar = showToobar, showShare = showShare, showWebUrlTitle = showWebUrlTitle))
    }

    override fun initData() {

    }

    companion object {
        const val URL = "url"
        const val TITILE = "title"
        const val SHOWTOOBAR = "showToobar"
        const val SHOWSHARE = "showShare"
        const val SHOWWEBURLTITLE = "showWebUrlTitle"

        /**
         * 跳转web浏览器页面
         *
         * @param context
         * @param url     链接
         */
        fun start(context: Context, url: String = "", title: String = "", showToobar: Boolean = true, showShare: Boolean = true, showWebUrlTitle: Boolean = true) {
            ARouter.getInstance()
                    .build(ARouterHub.BASELIB_WEBVIEW_ACTIVITY)
                    .withString(URL, url)
                    .withString(TITILE, title)
                    .withBoolean(SHOWTOOBAR, showToobar)
                    .withBoolean(SHOWSHARE, showShare)
                    .withBoolean(SHOWWEBURLTITLE, showWebUrlTitle)
                    .navigation(context)
        }
    }
}