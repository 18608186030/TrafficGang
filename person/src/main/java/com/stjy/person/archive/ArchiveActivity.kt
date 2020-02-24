package com.stjy.person.archive

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.mvvm.BaseVMActivity
import com.stjy.baselib.utils.ARouterHub
import com.stjy.person.R

@Route(path = ARouterHub.ARCHIVE_ACTIVITY, name = "我的档案模块")
class ArchiveActivity : BaseVMActivity<ArchiveViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
    }

    override fun viewModelClass() = ArchiveViewModel::class.java

    override fun initView() {
        loadRootFragment(R.id.llt_archive, ArchiveFragment.newInstance())
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
