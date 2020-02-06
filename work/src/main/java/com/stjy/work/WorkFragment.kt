package com.stjy.work

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.BaseFragment
import com.stjy.baselib.utils.ARouterHub

@Route(path = ARouterHub.WORK_FRAGMENT, name = "工作模块界面")
class WorkFragment : BaseFragment() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_work
    }

    override fun initView(contentView: View?) {

    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
