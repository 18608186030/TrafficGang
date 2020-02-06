package com.stjy.person

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.BaseFragment
import com.stjy.baselib.utils.ARouterHub

@Route(path = ARouterHub.PERSON_FRAGMENT, name = "个人中心块界面")
class PersonFragment : BaseFragment() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_person
    }

    override fun initView(contentView: View?) {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

}
