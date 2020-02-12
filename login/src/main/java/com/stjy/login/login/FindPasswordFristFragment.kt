package com.stjy.login.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_findpasswordfrist.*

@Route(path = ARouterHub.LOGIN_FINDPASSWORDFRIST_FRAGMENT, name = "找回密码第一级界面")
class FindPasswordFristFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): FindPasswordFristFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_FINDPASSWORDFRIST_FRAGMENT)
                    .navigation() as FindPasswordFristFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_findpasswordfrist

    override fun initView(contentView: View?) {

    }

    override fun initData() {
    }

    override fun initListener() {
        next.setOnClickListener {
            start(FindPasswordSecondFragment.newInstance())
        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
