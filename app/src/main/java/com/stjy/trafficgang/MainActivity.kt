package com.stjy.trafficgang

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.BaseActivity
import com.stjy.baselib.base.FragmentSubAdapter
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.wigiet.bottomview.BotBean
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Route(path = ARouterHub.APP_MAIN_ACTIVITY, name = "主页界面")
class MainActivity : BaseActivity() {

    private val fragments = ArrayList<Fragment>()
    private val itemIcon = ArrayList<BotBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        fragments.add(ARouter.getInstance().build(ARouterHub.WORK_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(ARouterHub.MAILLIST_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(ARouterHub.PERSON_FRAGMENT).navigation() as Fragment)
        itemIcon.add(BotBean("工作 ", R.mipmap.ic_home_normal, R.mipmap.ic_home_pressed))
        itemIcon.add(BotBean("通讯录", R.mipmap.ic_home_normal, R.mipmap.ic_home_pressed))
        itemIcon.add(BotBean("个人中心", R.mipmap.ic_home_normal, R.mipmap.ic_home_pressed))
        vp_content.adapter = FragmentSubAdapter(supportFragmentManager,fragments)
        vp_content.offscreenPageLimit = fragments.size
        bottom.setViewPager(vp_content, itemIcon) {

        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
