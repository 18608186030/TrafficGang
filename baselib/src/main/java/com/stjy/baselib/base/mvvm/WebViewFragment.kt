package com.stjy.baselib.base.mvvm

import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.R
import com.stjy.baselib.utils.ARouterHub
import kotlinx.android.synthetic.main.fragment_web.*


/**
 * @author daifalin
 * @date 2019-06-24 10:48
 * @ClassName WelfareFragment
 * @Description
 */
@Route(path = ARouterHub.BASELIB_WEBVIEW_FRAGMENT, name = "福利网页界面")
class WebViewFragment : BaseAgentWebFragment() {

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

    override fun initView(contentView: View?) {
        if (!showToobar) toolbar?.visibility = View.GONE
        if (!showShare) {
            ivShare?.visibility = View.GONE
        }
    }

    override fun getLayoutID() = R.layout.fragment_web

    override fun getAgentWebParent(): FrameLayout = fl_web

    override fun getUrl(): String? = webUrl

    override fun initData() {}

    override fun showWebUrlTitle(): Boolean {
        if (!showWebUrlTitle) {
            setBarTitle(title)
        }
        return showWebUrlTitle
    }

    override fun initListener() {
        ib_close.setOnClickListener {
            mActivity.finish()
        }
        ivShare.setOnClickListener {
            webView.callHandler("dataToJs", "Gson().toJson(user)") {

            }
        }
    }

    override fun setNavigationOnClickListener() {
        if (!onBackPressedSupport()) {
            mActivity.finish()
        }
    }

    companion object {
        const val URL = "url"
        const val TITILE = "title"
        const val SHOWTOOBAR = "showToobar"
        const val SHOWSHARE = "showShare"
        const val SHOWWEBURLTITLE = "showWebUrlTitle"

        @JvmStatic
        fun newInstance(url: String = "", title: String = "", showToobar: Boolean = true, showShare: Boolean = true, showWebUrlTitle: Boolean = true): WebViewFragment {
            return ARouter.getInstance()
                    .build("${ARouterHub.BASELIB_WEBVIEW_FRAGMENT}")
                    .withString(URL, url)
                    .withString(TITILE, title)
                    .withBoolean(SHOWTOOBAR, showToobar)
                    .withBoolean(SHOWSHARE, showShare)
                    .withBoolean(SHOWWEBURLTITLE, showWebUrlTitle)
                    .navigation() as WebViewFragment
        }
    }
}






