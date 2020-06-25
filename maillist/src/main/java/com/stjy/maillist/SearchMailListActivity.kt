package com.stjy.maillist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.GsonUtils
import com.stjy.baselib.base.mvvm.BaseVMActivity
import com.stjy.baselib.utils.ARouterHub
import com.stjy.maillist.adpater.MailListAdapter
import com.stjy.maillist.modle.MailListResp
import com.stjy.maillist.modle.MailListViewModel
import com.stjy.maillist.modle.SecondListBean
import kotlinx.android.synthetic.main.maillist_activity_searchhhmaillist.*


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
        recyclerview.layoutManager = LinearLayoutManager(this)
        //recyclerview.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        var adapter = MailListAdapter(ArrayList())
        recyclerview.adapter = adapter
        var a = """
            {
                "list":[
                    {
                        "title":"神通教育科技有限公司",
                        "list":[
                            {
                                "title":"总经办"
                            },
                            {
                                "title":"技术"
                            }
                        ]
                    },
                    {
                        "title":"熊猫科技有限公司",
                        "list":[
                            {
                                "title":"总经办"
                            },
                            {
                                "title":"技术"
                            }
                        ]
                    }
                ]
            }
        """

        var mailListRes = GsonUtils.fromJson<MailListResp>(a, MailListResp::class.java)
        adapter.setNewData(adapter.generateData((mailListRes.list)))
        adapter.setOnItemClickListener { adapter, view, position ->
                var itme = adapter.data[position] as SecondListBean
                ARouter.getInstance()
                        .build(ARouterHub.MAILLIST_SEARCHMAILLIST_ACTIVITY)
                        .withString("title", itme.title)
                        .navigation(this)
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
