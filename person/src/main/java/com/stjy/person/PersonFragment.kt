package com.stjy.person

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_person.*

@Route(path = ARouterHub.PERSON_FRAGMENT, name = "个人中心块界面")
class PersonFragment : BaseFragment() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_person
    }

    override fun initView(contentView: View?) {
        setBarTitle("个人中心")
        StatusBarUtils.setStatusBarColor(fakeStatusBar)
    }

    override fun initData() {

    }

    override fun initListener() {
        tv_my_archive.setOnClickListener {
            ARouter.getInstance()
                .build(ARouterHub.ARCHIVE_ACTIVITY)
                .navigation(mContext)
        }
    }

    override fun isShowBacking(): Boolean {
        return false
    }
}
