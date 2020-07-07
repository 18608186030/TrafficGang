package com.stjy.work.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.work.R
import com.stjy.work.viewmodel.WorkViewModel

@Route(path = ARouterHub.WORK_FRAGMENT, name = "工作模块界面")
class WorkFragment : BaseMVVMFragment<WorkViewModel>() {

    override fun viewModelClass() = WorkViewModel::class.java

    override fun getLayoutID(): Int = R.layout.fragment_work

    override fun onResume() {
        super.onResume()
        initStatusBar()
    }

    override fun initStatusBar() {
        super.initStatusBar()
        BarUtils.setStatusBarColor(mActivity, resources.getColor(R.color.red))
        toolbar?.let {
            it.setBackgroundResource(R.drawable.shap_toolbar_bg)
            BarUtils.addMarginTopEqualStatusBarHeight(it)
        }
    }

    override fun initView(contentView: View?) {
        //mViewModel.getListData(1)
        mStateView?.showRetry()
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun isShowBacking(): Boolean = false
}
