package com.stjy.login.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.mvvm.BaseMVVMActivity
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel

@Route(path = ARouterHub.LOGIN_ACTIVITY, name = "登陆模块")
class LoginActivity : BaseMVVMActivity<LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView() {
        loadRootFragment(R.id.llContentView, LoginFragment.newInstance())
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
