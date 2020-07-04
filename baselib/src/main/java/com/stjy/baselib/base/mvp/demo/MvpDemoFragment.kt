package com.stjy.baselib.base.mvp.demo

import android.view.View
import com.stjy.baselib.R
import com.stjy.baselib.base.mvp.BaseMVPFragment

class MvpDemoFragment : BaseMVPFragment<IViewMvpDemo?, PresenterMvpDemo?>() {

    override fun getLayoutID(): Int = R.layout.activity_mvpdemo

    override fun createPresenter(): PresenterMvpDemo {
        return PresenterMvpDemo()
    }

    override fun initView(contentView: View?) {}

    override fun initData() {
        mPresenter?.getListData("1")
    }

    override fun initListener() {}
}