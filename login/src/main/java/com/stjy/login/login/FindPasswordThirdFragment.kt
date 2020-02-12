package com.stjy.login.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_findpasswordthird.*

@Route(path = ARouterHub.LOGIN_FINDPASSWORDThird_FRAGMENT, name = "找回密码第一级界面")
class FindPasswordThirdFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): FindPasswordThirdFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_FINDPASSWORDThird_FRAGMENT)
                    .navigation() as FindPasswordThirdFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_findpasswordthird

    override fun initView(contentView: View?) {

    }

    override fun initData() {
    }

    override fun initListener() {
        ok.setOnClickListener {
            popTo(LoginFragment::class.java,false)
        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
    }
}
