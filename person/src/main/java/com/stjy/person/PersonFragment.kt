package com.stjy.person

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.stjy.baselib.base.mvc.BaseFragment
import com.stjy.baselib.ui.activity.WebViewActivity
import com.stjy.baselib.utils.ARouterHub
import kotlinx.android.synthetic.main.fragment_person.*

@Route(path = ARouterHub.PERSON_FRAGMENT, name = "个人中心块界面")
class PersonFragment : BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_person

    override fun initView(contentView: View?) {
        setBarTitle("个人中心")
    }

    override fun initData() {

    }

    override fun initListener() {

        tv_my_archive.onClick {
            WebViewActivity.start(mActivity, "https://www.pgyer.com", title = "郑仁超", showShare = false, showWebUrlTitle = false)
        }

        tvWodeXiaoxi.onClick {
            ARouter.getInstance()
                    .build(ARouterHub.BASELIB_MVPDEMO_ACTIVITY)
                    .navigation(mActivity)
        }

        tvJoinOrganize.onClick {
            ARouter.getInstance()
                    .build(ARouterHub.BASELIB_MVVMDEMO_ACTIVITY)
                    .navigation(mActivity)
        }

        tvOutLogin.onClick {
            ARouter.getInstance()
                    .build(ARouterHub.LOGIN_ACTIVITY)
                    .navigation(mActivity)
        }

        tvChangePassword.onClick {
            ARouter.getInstance()
                    .build(ARouterHub.ARCHIVE_ACTIVITY)
                    .navigation(mActivity)
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            statusBarColor(R.color.yellow)
        }
    }

    override fun isShowBacking(): Boolean = false
}
