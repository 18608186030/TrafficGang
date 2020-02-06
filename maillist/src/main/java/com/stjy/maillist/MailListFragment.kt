package com.stjy.maillist

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.BaseFragment
import com.stjy.baselib.utils.ARouterHub

@Route(path = ARouterHub.MAILLIST_FRAGMENT, name = "通讯录模块界面")
class MailListFragment : BaseFragment() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_maillist
    }

    override fun initView(contentView: View?) {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

}
