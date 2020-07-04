package com.stjy.person.archive

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.cmmont.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import com.stjy.person.R

@Route(path = ARouterHub.PERSON_INFO_FRAGMENT, name = "个人信息界面")
class PersonInfoFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): PersonInfoFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.PERSON_INFO_FRAGMENT)
                    .navigation() as PersonInfoFragment
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_person_info
    }

    override fun initView(contentView: View?) {
        setBarTitle("个人信息")
        fakeStatusBar()?.let {
            StatusBarUtils.setStatusBarColor(it)
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun isShowBacking(): Boolean {
        return false
    }
}
