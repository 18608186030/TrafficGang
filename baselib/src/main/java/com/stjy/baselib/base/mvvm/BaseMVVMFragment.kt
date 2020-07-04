package com.stjy.baselib.base.mvvm

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.stjy.baselib.base.cmmont.BaseFragment

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: ViewModel基类
 */
abstract class BaseMVVMFragment<V : BaseViewModel> : BaseFragment() {
    protected lateinit var mViewModel: V

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mViewModel = initViewModel()
        observeEvent(mViewModel)
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewModel(): V = obtainViewModel(viewModelClass())

    private fun observeEvent(viewModel: V) {
        viewModel.setLifecycleOwner(this)
        viewModel.actionLiveData.observe(this,
                Observer { actionEvent ->
                    when (actionEvent?.action) {
                        BaseMVVMActionEvent.START_LOADING -> startLoading()
                        BaseMVVMActionEvent.STOP_LOADING -> stopLoading()
                        BaseMVVMActionEvent.START_LOADING_DIALOG ->  startLoadingDialog()
                        BaseMVVMActionEvent.STOP_LOADING_DIALOG -> stopLoadingDialog()
                        BaseMVVMActionEvent.SHOW_TOAST -> ToastUtils.showShort(actionEvent.message)
                        BaseMVVMActionEvent.SHOW_ERROR -> showError(actionEvent.code, actionEvent.message)
                        BaseMVVMActionEvent.FINISH_REFRESH -> finishRefresh()
                        BaseMVVMActionEvent.FINISH_LOAD_MORE ->  finishLoadMore()
                        else -> {
                        }
                    }
                })
    }

    open fun showError(code: String?, message: String?) {
        mStateView.showRetry()
    }

    open fun finishRefresh() {}

    open fun finishLoadMore() {}

    abstract fun viewModelClass(): Class<V>
}