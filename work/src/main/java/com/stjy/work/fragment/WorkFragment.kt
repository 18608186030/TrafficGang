package com.stjy.work.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.work.R
import com.stjy.work.viewmodel.WorkViewModel

@Route(path = ARouterHub.WORK_FRAGMENT, name = "工作模块界面")
class WorkFragment : BaseVMFragment<WorkViewModel>() {

    override fun viewModelClass() = WorkViewModel::class.java

    override fun getLayoutID(): Int {
        return R.layout.fragment_work
    }

    override fun initView(contentView: View?) {
        mViewModel.getListData(1)
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}