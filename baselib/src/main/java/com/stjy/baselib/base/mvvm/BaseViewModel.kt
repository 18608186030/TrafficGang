package com.stjy.baselib.base.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.stjy.baselib.base.mvvm.IViewModelAction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: BaseViewModel基类
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IViewModelAction {

    private lateinit var lifecycleOwner: LifecycleOwner

    val actionLiveData: MutableLiveData<BaseMVVMActionEvent> = MutableLiveData()

    override fun startLoading() {
        startLoading("")
    }

    override fun startLoading(message: String?) {
        actionLiveData.value = BaseMVVMActionEvent(action = BaseMVVMActionEvent.START_LOADING,message=message)
    }

    override fun stopLoading() {
        actionLiveData.value = BaseMVVMActionEvent(action =BaseMVVMActionEvent.STOP_LOADING)
    }

    override fun startLoadingDialog() {
        actionLiveData.value = BaseMVVMActionEvent(action =BaseMVVMActionEvent.START_LOADING_DIALOG)
    }

    override fun stopLoadingDialog() {
        actionLiveData.value = BaseMVVMActionEvent(action = BaseMVVMActionEvent.STOP_LOADING_DIALOG)
    }

    override fun showToast(message: String?) {
        actionLiveData.value = BaseMVVMActionEvent(action =BaseMVVMActionEvent.SHOW_TOAST,message = message)
    }

    override fun showError() {
        showError("", "")
    }

    override fun showError(code: String?, message: String?) {
        actionLiveData.value = BaseMVVMActionEvent(action = BaseMVVMActionEvent.SHOW_ERROR,code = code,message = message)
    }

    override fun finishRefresh() {
        actionLiveData.value = BaseMVVMActionEvent(BaseMVVMActionEvent.FINISH_REFRESH)
    }

    override fun finishLoadMore() {
        actionLiveData.value = BaseMVVMActionEvent(BaseMVVMActionEvent.FINISH_LOAD_MORE)
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun getLifecycleOwner(): LifecycleOwner {
        return lifecycleOwner
    }

    protected fun <T> toObservable(observable: Observable<T>): Observable<T> {
        //网络请求在子线程，所以是在io线程，避免阻塞线程
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}