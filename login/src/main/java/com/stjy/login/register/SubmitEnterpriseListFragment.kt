package com.stjy.login.register

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SizeUtils
import com.stjy.baselib.base.mvvm.BaseVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import com.stjy.baselib.wigiet.divider.HorizontalDividerItemDecoration
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import com.stjy.login.register.adapter.SubmitEnterpriseListAdapter
import kotlinx.android.synthetic.main.fragment_submitenterpriselist.*

@Route(path = ARouterHub.LOGIN_SUBMITENTERPRISELIST_FRAGMENT, name = "提交信息列表")
class SubmitEnterpriseListFragment : BaseVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): SubmitEnterpriseListFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_SUBMITENTERPRISELIST_FRAGMENT)
                    .navigation() as SubmitEnterpriseListFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_submitenterpriselist

    override fun initView(contentView: View?) {
        setBarTitle("提交信息列表")
        StatusBarUtils.setStatusBarColor(fakeStatusBar())
        recyclerview.layoutManager = LinearLayoutManager(mActivity)
        recyclerview.addItemDecoration(HorizontalDividerItemDecoration.Builder(mActivity)
                .colorResId(R.color.colorPrimary)
                .size(SizeUtils.dp2px(10f))
                .build())
        var mVideoCourseAdapter = SubmitEnterpriseListAdapter(ArrayList())
        recyclerview.adapter = mVideoCourseAdapter
        var datas = arrayListOf("成都科技有限公司", "成都谛听科技股份有限公司", "成都哇哈哈科技有限公司", "成都王老吉科技有限公司")
        mVideoCourseAdapter.setNewData(datas)
        mVideoCourseAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tvDetails -> {
                    start(AuditDetailsFragment.newInstance())
                }

                else -> {

                }
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
