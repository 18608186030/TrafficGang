package com.stjy.baselib.net.subsciber;

import android.app.Dialog;

import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.subsciber.ProgressCancelListener;

/**
 * @author daifalin
 * @date 2018/11/23 2:45 PM
 * @ClassName ProgressObserver
 * @Description
 */
public abstract class ProgressObserver<T> extends BaseObserver<T> implements ProgressCancelListener {

    private IProgressDialog progressDialog;
    private Dialog mDialog;
    private boolean isShowProgress = true;

    public ProgressObserver(IProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        init(false);
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param progressDialog 对话框
     * @param isCancel       对话框是否可以取消
     */
    public ProgressObserver(IProgressDialog progressDialog, boolean isCancel) {
        this.progressDialog = progressDialog;
        init(isCancel);
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param progressDialog 对话框
     * @param isShowProgress 是否显示对话框
     * @param isCancel       对话框是否可以取消
     */
    public ProgressObserver(IProgressDialog progressDialog, boolean isShowProgress, boolean isCancel) {
        this.progressDialog = progressDialog;
        this.isShowProgress = isShowProgress;
        init(isCancel);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgress();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgress();
    }

    @Override
    public void onCancelProgress() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 初始化
     *
     * @param isCancel 对话框是否可以取消
     */
    private void init(boolean isCancel) {
        if (progressDialog == null) {
            return;
        }
        mDialog = progressDialog.getDialog();
        if (mDialog == null) {
            return;
        }
        mDialog.setCancelable(isCancel);
        if (isCancel) {
            mDialog.setOnCancelListener(dialogInterface -> ProgressObserver.this.onCancelProgress());
        }
    }

    /**
     * 展示进度框
     */
    private void showProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (!isShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }
}
