package com.stjy.maillist

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.base.mvvm.BaseVMActivity
import com.stjy.baselib.utils.ARouterHub
import com.stjy.maillist.modle.MailListViewModel


@Route(path = ARouterHub.MAILLIST_SEARCHMAILLIST_ACTIVITY, name = "搜索通讯录二级界面")
class SearchMailListActivity : BaseVMActivity<MailListViewModel>() {
    @Autowired(name = "title", desc = "机构名称")
    @JvmField
    var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maillist_activity_searchhhmaillist)
    }

    override fun viewModelClass() = MailListViewModel::class.java

    override fun initView() {
        setBarTitle(title)
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
