package com.stjy.person

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.stjy.baselib.base.mvvm.BaseFragment
import com.stjy.baselib.base.mvvm.WebViewActivity
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_person.*

@Route(path = ARouterHub.PERSON_FRAGMENT, name = "个人中心块界面")
class PersonFragment : BaseFragment() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_person
    }

    override fun initView(contentView: View?) {
        setBarTitle("个人中心")
        fakeStatusBar()?.let {
            StatusBarUtils.setStatusBarColor(it)
        }
    }

    override fun initData() {

    }

    override fun initListener() {
        tv_my_archive.setOnClickListener {
//            ARouter.getInstance()
//                .build(ARouterHub.ARCHIVE_ACTIVITY)
//                .navigation(mActivity)

//            WebViewActivity.start(mActivity, "https://www.pgyer.com", title = "郑仁超", showShare = false, showWebUrlTitle = false)
//            WebViewActivity.start(mActivity, "https://www.baidu.com")
            ToastUtils.showLong("我就是bug")
        }
        tvOutLogin.setOnClickListener {
            ARouter.getInstance()
                    .build(ARouterHub.LOGIN_ACTIVITY)
                    .navigation(mActivity)
        }
    }

    override fun isShowBacking(): Boolean {
        return false
    }
}
