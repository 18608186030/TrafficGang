package com.stjy.person.archive

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvc.BaseFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.person.R
import kotlinx.android.synthetic.main.fragment_archive.*

@Route(path = ARouterHub.ARCHIVE_FRAGMENT, name = "我的档案")
class ArchiveFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): ArchiveFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.ARCHIVE_FRAGMENT)
                    .navigation() as ArchiveFragment
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_archive
    }

    override fun initView(contentView: View?) {
        setBarTitle("我的档案")
//        StatusBarUtils.setStatusBarColor(fakeStatusBar)
    }

    override fun initData() {

    }

    override fun initListener() {
        tv_person_info.setOnClickListener {
            start(PersonInfoFragment.newInstance())
        }
    }

    override fun isShowBacking(): Boolean =false
}
