package com.stjy.baselib.ui.activity.mvpdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.stjy.baselib.R
import com.stjy.baselib.base.mvp.BaseMVPActivity
import com.stjy.baselib.bean.model.ZIXunAllListResp
import com.stjy.baselib.ui.adapter.SuperBaseQuickAdapter
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.ImageLoaderUtils
import com.stjy.baselib.wigiet.refreshheader.material.MaterialHeader
import com.zhouyou.http.exception.ApiException
import kotlinx.android.synthetic.main.activity_mvpdemo.*

@Route(path = ARouterHub.BASELIB_MVPDEMO_ACTIVITY, name = "MVP案例")
class MvpDemoActivity : BaseMVPActivity<MvpDemoActivity?, PresenterMvpDemo?>(), OnRefreshListener, OnLoadMoreListener {
    private lateinit var adapter: MvpDemoListAdapter
    private var pageNum = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpdemo)
    }

    override fun createPresenter(): PresenterMvpDemo? = PresenterMvpDemo()

    override fun initView() {
        setBarTitle("MVP框架使用案例")
        refreshLayout
                .setRefreshFooter(ClassicsFooter(this))
                .setRefreshHeader(MaterialHeader(this).setColorSchemeResources(R.color.colorAccent))
                .setOnRefreshListener(this)
                .setOnLoadMoreListener(this)
                .autoRefresh()
        adapter = MvpDemoListAdapter()
        adapter.initStaueView(this,refreshLayout)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    override fun initData() {

    }

    override fun initListener() {}

    fun getListDataSuccess(data: ZIXunAllListResp) {
        if (!data.other_list.isNullOrEmpty()) {
            data?.other_list?.forEach {
                adapter.addData(it)
            }
            pageNum++
            changeState()
        } else {
            adapter.showEmptyStaueView("你没有学习的课程哦！",R.mipmap.yingyezhizhao)
        }
    }

    fun getListDataError(e: ApiException) {
        changeState()
        if (adapter.data?.isEmpty()){
            adapter.showRetryStateView(e.message,R.mipmap.yingyezhizhao,"刷新", View.OnClickListener {
                mPresenter?.getListData(pageNum)
            })
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        adapter.data.clear()
        mPresenter?.getListData(pageNum)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter?.getListData(pageNum)
    }

    private fun changeState() {
        if (refreshLayout.state == RefreshState.Loading) {
            refreshLayout.finishLoadMore()
        }
        if (refreshLayout.state == RefreshState.Refreshing) {
            refreshLayout.finishRefresh()
        }
    }
}

class MvpDemoListAdapter: SuperBaseQuickAdapter<ZIXunAllListResp.OtherListBean, BaseViewHolder>(R.layout.layout_itme_mvpdemolist) {
    @SuppressLint("NewApi")
    override fun convert(helper: BaseViewHolder, item: ZIXunAllListResp.OtherListBean) {
        helper.setText(R.id.tvTitle, item.id.toString())
        ImageLoaderUtils.loadImage(mContext, item.image, helper.getView(R.id.ivImage))
    }
}