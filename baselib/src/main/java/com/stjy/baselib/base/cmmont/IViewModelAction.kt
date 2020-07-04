package com.stjy.baselib.base.cmmont


interface IViewModelAction {

    fun startLoading()

    fun startLoading(message: String?)

    fun stopLoading()

    fun startLoadingDialog()

    fun stopLoadingDialog()

    fun showToast(message: String?)

    fun showError()

    fun showError(code: String?, message: String?)

    fun finishRefresh()

    fun finishLoadMore()

}