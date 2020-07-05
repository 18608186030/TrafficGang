package com.stjy.baselib.ui.activity.mvpdemo

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.stjy.baselib.R
import com.stjy.baselib.base.mvp.BaseMVPActivity
import com.stjy.baselib.utils.ARouterHub

@Route(path = ARouterHub.BASELIB_MVPDEMO_ACTIVITY, name = "MVP案例")
class MvpDemoActivity : BaseMVPActivity<MvpDemoActivity?, PresenterMvpDemo?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpdemo)
    }

    override fun createPresenter(): PresenterMvpDemo? = PresenterMvpDemo()

    override fun initView() {
        setBarTitle("MVP框架使用案例")
    }
    override fun initData() {
        mPresenter?.getListData()
    }

    override fun initListener() {}

    fun getListDataSuccess(data: String) {

    }
}