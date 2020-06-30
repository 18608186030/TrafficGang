package com.stjy.work.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.stjy.baselib.net.net2.resp.BaseRespData
import com.stjy.baselib.base.mvvm.BaseViewModel
import com.stjy.baselib.net.net2.RetrofitHelper
import com.stjy.baselib.net.net2.observer.BaseObserver
import com.stjy.work.WorkApiService
import com.stjy.work.bean.ZiXunAllResp

class WorkViewModel(application: Application) : BaseViewModel(application) {

    /**
     * 网络请求2案例
     */
    var touguPageData = MutableLiveData<ZiXunAllResp>()
    fun getListData(page_num: Int) {
        toObservable(RetrofitHelper.create(WorkApiService::class.java).getListData(10, page_num)).subscribe(object : BaseObserver<BaseRespData<ZiXunAllResp>>() {
            override fun onSuccess(data: BaseRespData<ZiXunAllResp>) {
                super.onSuccess(data)
                touguPageData.value = data.data
                ToastUtils.showLong(data.data.toString())
            }
        })
    }
}
