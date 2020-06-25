package com.stjy.baselib.base.mvvm

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.stjy360.basicres.extend.obtainViewModel

/**
 * @author daifalin
 * @date 2018/12/4 5:37 PM
 * @ClassName BaseVMActivity
 * @Description ViewModel模式基类
 */
@SuppressLint("Registered")
abstract class BaseVMActivity<V : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        observeEvent(mViewModel)
    }

    private fun initViewModel(): V = obtainViewModel(viewModelClass())

    private fun observeEvent(viewModel: BaseViewModel) {
        viewModel.setLifecycleOwner(this)
        viewModel.actionLiveData.observe(this,
                Observer { actionEvent ->
                    when (actionEvent?.action) {
                        BaseActionEvent.START_LOADING -> startLoading()
                        BaseActionEvent.STOP_LOADING -> stopLoading()
                        BaseActionEvent.START_LOADING_DIALOG -> startLoadingDialog()
                        BaseActionEvent.STOP_LOADING_DIALOG -> stopLoadingDialog()
                        BaseActionEvent.SHOW_TOAST -> ToastUtils.showShort(actionEvent.message)
                        BaseActionEvent.SHOW_ERROR -> showError(actionEvent.code, actionEvent.message)
                        BaseActionEvent.FINISH_REFRESH -> finishRefresh()
                        BaseActionEvent.FINISH_LOAD_MORE -> finishLoadMore()
                        else -> {
                        }
                    }
                })
    }

    open fun showError(code: String?, message: String?) {
        mStateView.setRetryTitle(message).showRetry()
    }

    open fun finishRefresh() {}

    open fun finishLoadMore() {}

    abstract fun viewModelClass(): Class<V>
}
