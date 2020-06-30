package com.stjy.baselib.base.mvvm

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.R
import com.stjy.baselib.utils.ARouterHub
import kotlinx.android.synthetic.main.activity_standardweb.*


/**
 * @author acb
 * @date 2018/6/8 15:18
 * @ClassName StandardWebActivity.java
 * @Description <web页面>
 */
@Route(path = ARouterHub.BASELIB_WEBVIEW_ACTIVITY, name = "Web页面")
class StandardWebActivity : BaseAgentWebActivity() {
    @Autowired(name = URL, desc = "链接")
    lateinit var webUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standardweb)
    }

    override fun getUrl(): String =webUrl

    override fun getAgentWebParent(): ViewGroup =fl_web

    override fun initListener() {
        ivShare.setOnClickListener {
            webView.callHandler("dataToJs", "Gson().toJson(user)") {

            }
        }
    }

    override fun initView() {

    }

    override fun initData() {

    }

    companion object {
        const val URL = "url"

        /**
         * 跳转web浏览器页面
         *
         * @param context
         * @param url     链接
         */
        fun start(context: Context, url: String) {
            ARouter.getInstance()
                    .build(ARouterHub.BASELIB_WEBVIEW_ACTIVITY)
                    .withString(URL, url)
                    .navigation(context)
        }
    }
}