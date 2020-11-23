package com.stjy.login.register

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.utils.ARouterHub
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import com.stjy.login.register.adapter.SearchEnterpriseListAdapter
import kotlinx.android.synthetic.main.fragment_searchenterprise.*

@Route(path = ARouterHub.LOGIN_SEARCHENTERPRISE_FRAGMENT, name = "选择或创建企业")
class SearchEnterpriseFragment : BaseMVVMFragment<LoginViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(): SearchEnterpriseFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_SEARCHENTERPRISE_FRAGMENT)
                    .navigation() as SearchEnterpriseFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_searchenterprise

    override fun initView(contentView: View?) {
        setBarTitle("选择或创建企业")
        recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        recyclerview.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(mActivity, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
        var mVideoCourseAdapter = SearchEnterpriseListAdapter(ArrayList())
        recyclerview.adapter = mVideoCourseAdapter

        var datas = arrayListOf("成都科技有限公司", "成都谛听科技股份有限公司", "成都哇哈哈科技有限公司", "成都王老吉科技有限公司")
        mVideoCourseAdapter.setNewData(datas)
    }

    override fun initData() {
    }

    override fun initListener() {
        next.setOnClickListener {

        }
    }

    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }
}
