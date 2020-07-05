package com.stjy.baselib.ui.activity.mvpdemo

import com.stjy.baselib.base.mvp.BasePresenter
import com.stjy.baselib.bean.model.ConfigH5Bean
import com.stjy.baselib.net.BaseApi
import com.stjy.baselib.net.net1.HttpManager
import com.stjy.baselib.net.net1.callback.BaseCallBack
import com.zhouyou.http.exception.ApiException

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:
 */
class PresenterMvpDemo() : BasePresenter<MvpDemoActivity?>() {
    fun getListData() {
//        HttpManager.get(java.lang.String.format(BaseApi.DICT, "SYS_CONFIG_H5"))
//                .execute(ConfigH5Bean::class.java)
//                .subscribe(object : BaseObserver<ConfigH5Bean>() {
//                    override fun onSuccess(t: ConfigH5Bean) {
//
//                    }
//
//                    override fun onError(e: ApiException) {
//
//                    }
//                })

        view?.mDisposablePool?.add(
                HttpManager.get(java.lang.String.format(BaseApi.DICT, "SYS_CONFIG_H5"))
                        .execute(object : BaseCallBack<ConfigH5Bean>() {
                            override fun onStart() {
                                view?.mStateView?.showLoading()
                                super.onStart()
                            }

                            override fun onSuccess(data: ConfigH5Bean) {
                                view?.getListDataSuccess(data.toString())
                                view?.mStateView?.showContent()
                            }

                            override fun onError(e: ApiException) {
                                view?.mStateView?.showRetry()
                            }
                        })
        )
    }
}