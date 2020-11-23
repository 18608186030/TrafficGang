package com.stjy.baselib.base.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.stjy.baselib.base.mvc.BaseActivity

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: ViewModel模式基类
 */
@SuppressLint("Registered")
abstract class BaseMVVMActivity<V : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        observeEvent(mViewModel)
    }

    private fun initViewModel(): V = obtainViewModelWithActivity(viewModelClass())

    private fun observeEvent(viewModel: BaseViewModel) {
        viewModel.setLifecycleOwner(this)
        viewModel.actionLiveData.observe(this,
                Observer { actionEvent ->
                    when (actionEvent?.action) {
                        BaseMVVMActionEvent.START_LOADING -> startLoading()
                        BaseMVVMActionEvent.STOP_LOADING -> stopLoading()
                        BaseMVVMActionEvent.START_LOADING_DIALOG -> startLoadingDialog()
                        BaseMVVMActionEvent.STOP_LOADING_DIALOG -> stopLoadingDialog()
                        BaseMVVMActionEvent.SHOW_TOAST -> ToastUtils.showShort(actionEvent.message)
                        BaseMVVMActionEvent.SHOW_ERROR -> showError(actionEvent.code, actionEvent.message)
                        BaseMVVMActionEvent.FINISH_REFRESH -> finishRefresh()
                        BaseMVVMActionEvent.FINISH_LOAD_MORE -> finishLoadMore()
                        else -> {
                        }
                    }
                })
    }

    open fun showError(code: String?, message: String?) {
        mStateView?.showRetry(retryTitle = message)
    }

    open fun finishRefresh() {}

    open fun finishLoadMore() {}

    abstract fun viewModelClass(): Class<V>
}
