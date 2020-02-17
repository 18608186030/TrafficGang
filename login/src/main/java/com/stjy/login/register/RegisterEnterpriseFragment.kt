package com.stjy.login.register

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_registerenterprise.*

@Route(path = ARouterHub.LOGIN_REGISTERENTERPRISE_FRAGMENT, name = "注册企业")
class RegisterEnterpriseFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): RegisterEnterpriseFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_REGISTERENTERPRISE_FRAGMENT)
                    .navigation() as RegisterEnterpriseFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_registerenterprise

    override fun initView(contentView: View?) {
        setBarTitle("注册企业")
        setBarRightText("保存")
        StatusBarUtils.setStatusBarColor(fakeStatusBar)
    }

    override fun initData() {
    }

    override fun initListener() {
        submit.setOnClickListener() {
            start(SubmitEnterpriseListFragment.newInstance())
        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
