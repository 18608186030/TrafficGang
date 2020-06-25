package com.stjy.baselib.base.mvvm

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import obtainViewModel

/**
 * @author daifalin
 * @date 2018/12/4 5:50 PM
 * @ClassName BaseVMFragment
 * @Description ViewModel基类
 */
abstract class BaseVMFragment<V : BaseViewModel> : BaseFragment() {
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
                        BaseActionEvent.START_LOADING -> startLoading()
                        BaseActionEvent.STOP_LOADING -> stopLoading()
                        BaseActionEvent.START_LOADING_DIALOG ->  startLoadingDialog()
                        BaseActionEvent.STOP_LOADING_DIALOG -> stopLoadingDialog()
                        BaseActionEvent.SHOW_TOAST -> ToastUtils.showShort(actionEvent.message)
                        BaseActionEvent.SHOW_ERROR -> showError(actionEvent.code, actionEvent.message)
                        BaseActionEvent.FINISH_REFRESH -> finishRefresh()
                        BaseActionEvent.FINISH_LOAD_MORE ->  finishLoadMore()
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