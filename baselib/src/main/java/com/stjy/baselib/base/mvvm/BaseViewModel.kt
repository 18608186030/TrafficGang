package com.stjy.baselib.base.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.stjy360.basicres.IViewModelAction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author daifalin
 * @date 2018/12/5 3:51 PM
 * @ClassName BaseViewModel
 * @Description
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IViewModelAction {

    private lateinit var lifecycleOwner: LifecycleOwner

    val actionLiveData: MutableLiveData<BaseActionEvent> = MutableLiveData()

    override fun startLoading() {
        startLoading("")
    }

    override fun startLoading(message: String?) {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.START_LOADING)
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
    }

    override fun stopLoading() {
        actionLiveData.value = BaseActionEvent(BaseActionEvent.STOP_LOADING)
    }

    override fun startLoadingDialog() {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.START_LOADING_DIALOG)
        actionLiveData.value = baseActionEvent
    }

    override fun stopLoadingDialog() {
        actionLiveData.value = BaseActionEvent(BaseActionEvent.STOP_LOADING_DIALOG)
    }

    override fun showToast(message: String?) {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.SHOW_TOAST)
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
    }

    override fun showError() {
        showError("", "")
    }

    override fun showError(code: String?, message: String?) {
        val baseActionEvent = BaseActionEvent(BaseActionEvent.SHOW_ERROR)
        baseActionEvent.message = code
        baseActionEvent.message = message
        actionLiveData.value = baseActionEvent
    }

    override fun finishRefresh() {
        actionLiveData.value = BaseActionEvent(BaseActionEvent.FINISH_REFRESH)
    }

    override fun finishLoadMore() {
        actionLiveData.value = BaseActionEvent(BaseActionEvent.FINISH_LOAD_MORE)
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