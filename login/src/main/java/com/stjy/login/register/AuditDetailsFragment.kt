package com.stjy.login.register

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import com.stjy.login.register.adapter.TimeLineListAdapter
import kotlinx.android.synthetic.main.fragment_auditdetails.*

@Route(path = ARouterHub.LOGIN_AUDITDETAILS_FRAGMENT, name = "企业注册信息审核详情")
class AuditDetailsFragment : BaseMVVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): AuditDetailsFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_AUDITDETAILS_FRAGMENT)
                    .navigation() as AuditDetailsFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_auditdetails

    override fun initView(contentView: View?) {
        setBarTitle("企业注册信息审核详情")
        recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        var mVideoCourseAdapter = TimeLineListAdapter(ArrayList())
        recyclerview.adapter = mVideoCourseAdapter
        var datas = arrayListOf("成都科技有限公司", "成都谛听科技股份有限公司", "成都哇哈哈科技有限公司", "成都王老吉科技有限公司")
        mVideoCourseAdapter.setNewData(datas)
        mVideoCourseAdapter.setOnItemChildClickListener { adapter, view, position ->


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
