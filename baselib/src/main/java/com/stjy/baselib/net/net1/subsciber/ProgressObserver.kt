package com.stjy.baselib.net.net1.subsciber

import android.app.Dialog
import android.content.DialogInterface
import com.zhouyou.http.subsciber.IProgressDialog
import com.zhouyou.http.subsciber.ProgressCancelListener

/**
 * @author daifalin
 * @date 2018/11/23 2:45 PM
 * @ClassName ProgressObserver
 * @Description
 */
abstract class ProgressObserver<T> : BaseObserver<T>, ProgressCancelListener {
    private var progressDialog: IProgressDialog?
    private var mDialog: Dialog? = null
    private var isShowProgress = true

    constructor(progressDialog: IProgressDialog?) {
        this.progressDialog = progressDialog
        init(false)
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param progressDialog 对话框
     * @param isCancel       对话框是否可以取消
     */
    constructor(progressDialog: IProgressDialog?, isCancel: Boolean) {
        this.progressDialog = progressDialog
        init(isCancel)
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param progressDialog 对话框
     * @param isShowProgress 是否显示对话框
     * @param isCancel       对话框是否可以取消
     */
    constructor(progressDialog: IProgressDialog?, isShowProgress: Boolean, isCancel: Boolean) {
        this.progressDialog = progressDialog
        this.isShowProgress = isShowProgress
        init(isCancel)
    }

    override fun onStart() {
        super.onStart()
        showProgress()
    }

    override fun onComplete() {
        super.onComplete()
        dismissProgress()
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        dismissProgress()
    }

    override fun onCancelProgress() {
        if (!disposable!!.isDisposed) {
            disposable?.dispose()
        }
    }

    /**
     * 初始化
     *
     * @param isCancel 对话框是否可以取消
     */
    private fun init(isCancel: Boolean) {
        if (progressDialog == null) {
            return
        }
        mDialog =mDialog?: progressDialog?.dialog
        mDialog?.setCancelable(isCancel)
        if (isCancel) {
            mDialog?.setOnCancelListener { dialogInterface: DialogInterface? -> onCancelProgress() }
        }
    }

    /**
     * 展示进度框
     */
    private fun showProgress() {
        if (!isShowProgress) {
            return
        }
        mDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    /**
     * 取消进度框
     */
    private fun dismissProgress() {
        if (!isShowProgress) {
            return
        }
        mDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}