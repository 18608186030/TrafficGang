package com.stjy.login.register

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.stjy.baselib.base.mvvm.BaseMVVMFragment
import com.stjy.baselib.listener.PermissionListener
import com.stjy.baselib.utils.ARouterHub
import com.stjy.baselib.utils.MatisseUtils
import com.stjy.baselib.utils.PhotoPreviewUtils
import com.stjy.baselib.utils.StatusBarUtils
import com.stjy.login.R
import com.stjy.login.model.LoginViewModel
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.fragment_registerenterprise.*

@Route(path = ARouterHub.LOGIN_REGISTERENTERPRISE_FRAGMENT, name = "注册企业")
class RegisterEnterpriseFragment : BaseMVVMFragment<LoginViewModel>() {
    //营业制造
    var businessLicenseUrl: String = ""

    companion object {
        @JvmStatic
        fun newInstance(): RegisterEnterpriseFragment {
            return ARouter.getInstance()
                    .build(ARouterHub.LOGIN_REGISTERENTERPRISE_FRAGMENT)
                    .navigation() as RegisterEnterpriseFragment
        }
    }

    override fun viewModelClass() = LoginViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_registerenterprise

    override fun initView(contentView: View?) {
        setBarTitle("注册企业")
        setBarRightText("保存")
        fakeStatusBar()?.let {
            StatusBarUtils.setStatusBarColor(it)
        }
        initBusinessLicense()
    }

    override fun initData() {
    }

    override fun initListener() {
        submit.setOnClickListener() {
            start(SubmitEnterpriseListFragment.newInstance())
        }
    }

    /**
     * 显示驾驶证图片
     */
    private fun initBusinessLicense() {
        if (businessLicenseUrl != null) {
            Glide.with(mActivity).load(businessLicenseUrl).into(ivBusinessLicense)
            ivBusinessLicense.setOnClickListener {
                if (businessLicenseUrl.isNullOrEmpty()) {
                    requestPermission(object : PermissionListener {
                        override fun onGranted() {
                            MatisseUtils.fromImage(this@RegisterEnterpriseFragment)
                                    .maxSelectable(1)
                                    .forResult(1100)
                        }
                    }, MatisseUtils.PERMISSION.toString())
                } else {
                    PhotoPreviewUtils.start(mActivity, 0, arrayListOf(businessLicenseUrl))
                }
            }
            ivBusinessLicenseDelete.setOnClickListener {
                businessLicenseUrl = ""
                ivBusinessLicenseDelete.visibility = View.GONE
                ivBusinessLicense.setImageResource(R.mipmap.yingyezhizhao)
            }
        }
    }


    override fun isShowBacking() = true

    override fun setNavigationOnClickListener() {
        pop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val multipleResult = Matisse.obtainPathResult(data)
            val imagePath: String? = multipleResult[0]
            when (requestCode) {
                1100 -> {
                    Glide.with(mActivity).load(imagePath).into(ivBusinessLicense)
                    ivBusinessLicenseDelete.visibility = View.VISIBLE
                }

                else -> {

                }
            }
            //uploadImage(imagePath, requestCode)
        }
    }

}
