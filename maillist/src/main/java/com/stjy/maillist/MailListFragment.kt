package com.stjy.maillist

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.GsonUtils
import com.stjy.baselib.base.mvc.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.maillist.adpater.MailListAdapter
import com.stjy.maillist.modle.MailListResp
import com.stjy.maillist.modle.SecondListBean
import kotlinx.android.synthetic.main.fragment_maillist.*

@Route(path = ARouterHub.MAILLIST_FRAGMENT, name = "通讯录模块界面")
class MailListFragment : BaseFragment() {
    override fun getLayoutID(): Int = R.layout.fragment_maillist
    override fun initStatusBar() {
        super.initStatusBar()
        BarUtils.setStatusBarColor(mActivity, resources.getColor(R.color.blue))
        toolbar?.let {
            it.setBackgroundResource(R.drawable.shap_toolbar_bg)
            BarUtils.addMarginTopEqualStatusBarHeight(it)
        }
    }

    override fun initView(contentView: View?) {
        setBarTitle("通讯录")
        recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
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
            run {
                var itme = adapter.data[position] as SecondListBean
                ARouter.getInstance()
                        .build(ARouterHub.MAILLIST_SEARCHMAILLIST_ACTIVITY)
                        .withString("title", itme.title)
                        .navigation(mActivity)
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun isShowBacking(): Boolean = false
}
