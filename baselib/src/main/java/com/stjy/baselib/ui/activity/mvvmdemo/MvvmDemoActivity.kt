package com.stjy.baselib.ui.activity.mvvmdemo

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import com.stjy.baselib.base.mvvm.BaseMVVMActivity
import com.stjy.baselib.bean.model.ZIXunAllListResp
import com.stjy.baselib.ui.adapter.SuperBaseQuickAdapter
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.ImageLoaderUtils
import com.stjy.baselib.wigiet.refreshheader.material.MaterialHeader
import kotlinx.android.synthetic.main.activity_mvvmdemo.*

@Route(path = ARouterHub.BASELIB_MVVMDEMO_ACTIVITY, name = "MVVM案例")
class MvvmDemoActivity : BaseMVVMActivity<MvvmDemoViewModel>(), OnLoadMoreListener {

    private lateinit var adapter: MvpDemoListAdapter
    private var pageNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvmdemo)
    }

    override fun viewModelClass() = MvvmDemoViewModel::class.java

    override fun initView() {
        setBarTitle("MVVM框架使用案例")
        mViewModel?.getListData(1)
        refreshLayout
                .setRefreshFooter(ClassicsFooter(this))
                .setRefreshHeader(MaterialHeader(this).setColorSchemeResources(R.color.colorAccent))
                .setEnableRefresh(false)
                .setOnLoadMoreListener(this)
                //.autoRefresh()
        adapter = MvpDemoListAdapter()
        adapter.initStaueView(this, refreshLayout)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    override fun initData() {
        pageNum = 1
        adapter.data.clear()
        adapter.showLoadingStateView()
        mViewModel.getListData(pageNum)
        mViewModel.touguPageData.observe(this, Observer {
            if (it != null) {
                it?.other_list?.let { it1 ->
                    if (it1.isNotEmpty()) {
                        it1.forEach { it2 ->
                            adapter.addData(it2)
                        }
                        pageNum++
                        changeState()
                    } else {
                        adapter.showEmptyStaueView("你没有学习的课程哦", R.mipmap.yingyezhizhao)
                    }
                }
            } else {
                changeState()
                if (adapter.data?.isEmpty()) {
                    adapter.showRetryStateView(iccon = R.mipmap.yingyezhizhao, retry = "从新加载", btnClick = View.OnClickListener {
                        pageNum = 1
                        adapter.data.clear()
                        adapter.showLoadingStateView()
                        mViewModel.getListData(pageNum)
                    })
                }
            }
        })
    }

    override fun initListener() {}

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.getListData(pageNum)
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

class MvpDemoListAdapter : SuperBaseQuickAdapter<ZIXunAllListResp.OtherListBean, BaseViewHolder>(R.layout.layout_itme_mvpdemolist) {
    @SuppressLint("NewApi")
    override fun convert(helper: BaseViewHolder, item: ZIXunAllListResp.OtherListBean) {
        helper.setText(R.id.tvTitle, item.id.toString())
        ImageLoaderUtils.loadImage(mContext, item.image, helper.getView(R.id.ivImage))
    }
}