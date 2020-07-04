package com.stjy.login.login

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import kotlinx.android.synthetic.main.fragment_findpasswordfrist.*

@Route(path = ARouterHub.LOGIN_FINDPASSWORDSECOND_FRAGMENT, name = "找回密码第一级界面")
class FindPasswordSecondFragment : BaseMVVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): FindPasswordSecondFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_FINDPASSWORDSECOND_FRAGMENT)
                    .navigation() as FindPasswordSecondFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_findpasswordsecond

    override fun initView(contentView: View?) {

    }

    override fun initData() {
    }

    override fun initListener() {
        next.setOnClickListener {
            start(FindPasswordThirdFragment.newInstance())
        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
