package com.stjy.baselib.ui.activity.mvvmdemo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.stjy.baselib.base.mvvm.BaseViewModel
import com.stjy.baselib.bean.model.ZIXunAllListResp
import com.stjy.baselib.net.net2.BaseApiService
import com.stjy.baselib.net.net2.RetrofitHelper
import com.stjy.baselib.net.net2.observer.BaseObserver
import com.stjy.baselib.net.net2.resp.BaseRespData

/**
 * @Author: superman
 * @CreateTime: 2020/7/7
 * @Describe:
 */
class MvvmDemoViewModel(application: Application) : BaseViewModel(application) {
    /**
     * 网络请求2案例
     */
    var touguPageData = MutableLiveData<ZIXunAllListResp>()
    fun getListData(page_num: Int) {
        toObservable(RetrofitHelper.create(BaseApiService::class.java).getListData(10, page_num)).subscribe(object : BaseObserver<BaseRespData<ZIXunAllListResp>>() {
            override fun onSuccess(data: BaseRespData<ZIXunAllListResp>) {
                super.onSuccess(data)
                touguPageData.value = data.data
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                touguPageData.value = null
            }
        })
    }
}