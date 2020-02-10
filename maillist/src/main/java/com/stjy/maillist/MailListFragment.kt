package com.stjy.maillist

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.stjy.baselib.base.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.maillist.adpater.MailListAdapter
import com.stjy.maillist.modle.MailListResp
import kotlinx.android.synthetic.main.fragment_maillist.*

@Route(path = ARouterHub.MAILLIST_FRAGMENT, name = "通讯录模块界面")
class MailListFragment : BaseFragment() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_maillist
    }

    override fun initView(contentView: View?) {
        setBarTitle("通讯录")

        recyclerview.layoutManager = LinearLayoutManager(mContext)
        recyclerview.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        var mVideoCourseAdapter = MailListAdapter(ArrayList())
        recyclerview.adapter = mVideoCourseAdapter
        var a = """
            {
    "list":[
        {
            "title":"第一层",
            "list":[
                {
                    "title":"第二层",
                    "list":[
                        {
                            "title":"第三层"
                        },
                        {
                            "title":"第三层"
                        },
                        {
                            "title":"第三层"
                        }
                    ]
                },
                {
                    "title":"第一层",
                    "list":[
                        {
                            "title":"第三层"
                        },
                        {
                            "title":"第三层"
                        },
                        {
                            "title":"第三层"
                        }
                    ]
                }
            ]
        },
        {
            "title":"2第一层",
            "list":[
                {
                    "title":"2第二层",
                    "list":[
                        {
                            "title":"2第三层"
                        },
                        {
                            "title":"2第三层"
                        },
                        {
                            "title":"2第三层"
                        }
                    ]
                },
                {
                    "title":"2第一层",
                    "list":[
                        {
                            "title":"2第三层"
                        },
                        {
                            "title":"2第三层"
                        },
                        {
                            "title":"2第三层"
                        }
                    ]
                }
            ]
        }
    ]
}
        """

        var mailListRes = GsonUtils.fromJson<MailListResp>(a, MailListResp::class.java)
        //数据分组
        mVideoCourseAdapter.setNewData(generateData(mailListRes?.list))
    }

    /**
     * 处理课程数据
     *
     * @param outlineList
     * @return
     */
    private fun generateData(mailList: List<MailListResp.ListBeanXX>?): List<MultiItemEntity> {
        val res = java.util.ArrayList<MultiItemEntity>()
        mailList?.forEach { listBeanXX ->
            res.add(listBeanXX)
            listBeanXX.list?.forEach { listBeanX ->
                res.add(listBeanX)
                listBeanX.list?.forEach { listBean ->
                    res.add(listBean)
                }
            }
        }
        return res
    }


    override fun initData() {

    }

    override fun initListener() {

    }

    override fun isShowBacking(): Boolean {
        return false
    }
}
