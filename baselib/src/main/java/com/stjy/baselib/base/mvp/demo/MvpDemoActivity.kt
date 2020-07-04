package com.stjy.baselib.base.mvp.demo

import android.os.Bundle
import com.stjy.baselib.R
import com.stjy.baselib.base.mvp.BaseMVPActivity

class MvpDemoActivity : BaseMVPActivity<IViewMvpDemo?, PresenterMvpDemo?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpdemo)
    }

    override fun createPresenter(): PresenterMvpDemo {
        return PresenterMvpDemo()
    }

    override fun initView() {}
    override fun initData() {
        mPresenter?.getListData("1")
    }
    override fun initListener() {}
}