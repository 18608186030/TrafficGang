package com.stjy.work

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import kotlinx.android.synthetic.main.fragment_work.*

@Route(path = ARouterHub.WORK_FRAGMENT, name = "工作模块界面")
class WorkFragment : BaseFragment() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_work
    }

    override fun initView(contentView: View?) {

    }

    override fun initData() {

    }

    override fun initListener() {
        login.setOnClickListener {
            ARouter.getInstance()
                    .build(ARouterHub.LOGIN_ACTIVITY)
                    .navigation(mActivity)
        }
    }
}
