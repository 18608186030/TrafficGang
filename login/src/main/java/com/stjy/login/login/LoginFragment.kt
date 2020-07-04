package com.stjy.login.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import com.stjy.login.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

@Route(path = ARouterHub.LOGIN_FRAGMENT, name = "工作模块界面")
class LoginFragment  : BaseMVVMFragment<LoginViewModel>(){

    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_FRAGMENT)
                    .navigation() as LoginFragment
        }
    }

    override fun viewModelClass()= LoginViewModel::class.java

    override fun getLayoutID()= R.layout.fragment_login

    override fun initView(contentView: View?) {

    }

    override fun initData() {
    }

    override fun initListener() {
        forgetPassWord.setOnClickListener {
            start(FindPasswordFristFragment.newInstance())
        }

        register.setOnClickListener {
            start(RegisterFragment.newInstance())
        }
    }
}
