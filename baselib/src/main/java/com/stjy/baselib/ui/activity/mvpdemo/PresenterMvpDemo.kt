package com.stjy.baselib.ui.activity.mvpdemo

import com.stjy.baselib.base.mvp.BasePresenter
import com.stjy.baselib.bean.model.ZIXunAllListResp
import com.stjy.baselib.net.BaseApi
import com.stjy.baselib.net.net1.HttpManager
import com.stjy.baselib.net.net1.callback.BaseCallBack
import com.zhouyou.http.exception.ApiException
import kotlin.collections.HashMap

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe:
 */
class PresenterMvpDemo() : BasePresenter<MvpDemoActivity?>() {
    fun getListData(page_num: Int) {

        val params = HashMap<String, String>()
        params["page_size"] = "${10}"
        params["page_num"] = "$page_num"
//        HttpManager.get(BaseApi.INFORMATION)
//                .params(params)
//                .execute(ZIXunAllListResp::class.java)
//                .subscribe(object : BaseObserver<ZIXunAllListResp>() {
//                    override fun onSuccess(t: ZIXunAllListResp) {
//
//                    }
//
//                    override fun onError(e: ApiException) {
//
//                    }
//                })
//        view?.mDisposablePool?.add(
//                HttpManager.get(BaseApi.INFORMATION)
//                        .params(params)
//                        .execute(object : BaseCallBack<ZIXunAllListResp>() {
//                            override fun onStart() {
//                                view?.startLoadingDialog()
//                                view?.mStateView?.showLoading()
//                                super.onStart()
//                            }
//
//                            override fun onSuccess(data: ZIXunAllListResp) {
//                                view?.stopLoadingDialog()
//                                view?.mStateView?.showContent()
//                                view?.getListDataSuccess(data)
//                            }
//
//                            override fun onError(e: ApiException) {
//                                view?.stopLoadingDialog()
//                                view?.mStateView?.showRetry()
//                            }
//                        })
//        )
        view?.mDisposablePool?.add(
                HttpManager.get(BaseApi.INFORMATION)
                        .params(params)
                        .execute(object : BaseCallBack<ZIXunAllListResp>() {

                            override fun onSuccess(data: ZIXunAllListResp) {
                                view?.getListDataSuccess(data)
                            }

                            override fun onError(e: ApiException) {
                                view?.getListDataError(e)
                            }
                        }))

    }

}