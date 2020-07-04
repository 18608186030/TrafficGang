package com.stjy.baselib.base.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
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
        val baseActionEvent = BaseMVVMActionEvent(BaseMVVMActionEvent.START_LOADING)
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
    }

    override fun stopLoading() {
        actionLiveData.value = BaseMVVMActionEvent(BaseMVVMActionEvent.STOP_LOADING)
    }

    override fun startLoadingDialog() {
        val baseActionEvent = BaseMVVMActionEvent(BaseMVVMActionEvent.START_LOADING_DIALOG)
        actionLiveData.value = baseActionEvent
    }

    override fun stopLoadingDialog() {
        actionLiveData.value = BaseMVVMActionEvent(BaseMVVMActionEvent.STOP_LOADING_DIALOG)
    }

    override fun showToast(message: String?) {
        val baseActionEvent = BaseMVVMActionEvent(BaseMVVMActionEvent.SHOW_TOAST)
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
    }

    override fun showError() {
        showError("", "")
    }

    override fun showError(code: String?, message: String?) {
        val baseActionEvent = BaseMVVMActionEvent(BaseMVVMActionEvent.SHOW_ERROR)
        baseActionEvent.message = code
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
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
        return observable.subscribeOn(Schedulers.io())//网络请求在子线程，所以是在io线程，避免阻塞线程
                .observeOn(AndroidSchedulers.mainThread())
    }
}