package com.stjy.login.register

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*

@Route(path = ARouterHub.LOGIN_REGISTER_FRAGMENT, name = "找回密码第一级界面")
class RegisterFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): RegisterFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_REGISTER_FRAGMENT)
                    .navigation() as RegisterFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_register

    override fun initView(contentView: View?) {
        setBarTitle("注册")
        fakeStatusBar()?.let {
            StatusBarUtils.setStatusBarColor(it)
        }
    }

    override fun initData() {
    }

    override fun initListener() {
        next.setOnClickListener() {
            start(CheckEnterpriseModeFragment.newInstance())
        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
