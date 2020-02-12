package com.stjy.login.register

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel

@Route(path = ARouterHub.LOGIN_CHECKENTERPRISEMODE_FRAGMENT, name = "选择或创建企业")
class CheckEnterpriseModeFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): CheckEnterpriseModeFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_CHECKENTERPRISEMODE_FRAGMENT)
                    .navigation() as CheckEnterpriseModeFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_checkenterprisemode

    override fun initView(contentView: View?) {
        setBarTitle("选择或创建企业")
    }

    override fun initData() {
    }

    override fun initListener() {

    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
    }
}
