package com.stjy.baselib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.stjy.baselib.R;
import com.stjy.baselib.listener.PermissionListener;
import com.stjy.baselib.utils.EventBusUtils;
import com.stjy.baselib.utils.RxLifecycleUtils;
import com.stjy.baselib.wigiet.stateview.StateView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;

/**
 * @author DaiFalin
 */
public abstract class BaseFragment extends SupportFragment implements View.OnClickListener {

    protected Context mContext;
    protected View mView;
    protected StateView mStateView;
    protected CompositeDisposable mDisposablePool = new CompositeDisposable();
    private BaseActivity mActivity;
    private TextView mBarTitle;
    private TextView mBarRight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mContext = context;
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.mContext = activity;
            this.mActivity = (BaseActivity) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutID(), container, false);
        mStateView = StateView.inject(mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolBar();
        initView(mView);
        initData();
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isRegisterEvent()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRegisterEvent()) {
            EventBusUtils.unregister(this);
        }
        if (mActivity.mProgressDialog != null) {
            mActivity.mProgressDialog.dismiss();
        }
        mDisposablePool.clear();
    }

    /**
     * 是否订阅Bus事件
     *
     * @return
     */
    public boolean isRegisterEvent() {
        return false;
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mBarTitle = mView.findViewById(R.id.bar_title);
        mBarRight = mView.findViewById(R.id.bar_right);
        //判断是否有Toolbar,并默认显示返回按钮
        if (null != getToolbar() && isShowBacking()) {
            setNavigationIcon(R.mipmap.go_back_black);
            getToolbar().setNavigationOnClickListener(v -> {
                setNavigationOnClickListener();
            });
        }
    }

    public void setNavigationOnClickListener() {
        mActivity.finish();
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setBarTitle(CharSequence title) {
        if (null != mBarTitle) {
            mBarTitle.setText(title);
        }
    }

    /**
     * 设置右边的文字
     *
     * @return
     */
    public void setBarRightText(CharSequence subTitle) {
        if (null != mBarRight) {
            mBarRight.setText(subTitle);
        }
    }

    /**
     * 设置右边的文字的点击监听
     *
     * @param listener
     */
    public void setOnClickRightTextListener(View.OnClickListener listener) {
        if (null != mBarRight && null != listener) {
            mBarRight.setOnClickListener(listener);
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return mView.findViewById(R.id.toolbar);
    }

    protected <T> LifecycleTransformer<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    /**
     * 后退按钮图片
     */
    public void setNavigationIcon(@DrawableRes int resId) {
        getToolbar().setNavigationIcon(resId);
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    public boolean isShowBacking() {
        return true;
    }

    /**
     * 打开新的Activity
     *
     * @param toActivity 新Activity
     */
    public void startActivity(Class<? extends AppCompatActivity> toActivity) {
        startActivity(toActivity, false);
    }

    /**
     * 打开新的Activity
     *
     * @param toActivity 新Activity
     * @param isFinish   是否关闭当前Activity
     */
    public void startActivity(Class<? extends AppCompatActivity> toActivity, boolean isFinish) {
        ActivityUtils.startActivity(getBaseActivity(), toActivity);
        if (isFinish) {
            getBaseActivity().finish();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Fragment fragment = getParentFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void startLoadingDialog() {
        mActivity.startLoadingDialog();
    }

    public void stopLoadingDialog() {
        mActivity.stopLoadingDialog();
    }

    public void startLoading() {
        if (mStateView != null) {
            mStateView.showLoading();
        }

    }

    public void stopLoading() {
        if (mStateView != null) {
            mStateView.showContent();
        }
    }

    public IProgressDialog getIProgressDialog() {
        return this::getProgressDialog;
    }

    public Dialog getProgressDialog() {
        return new QMUITipDialog.Builder(getBaseActivity())
                .setIconType(ICON_TYPE_LOADING)
                .setTipWord("加载中")
                .create(false);
        /*return new MaterialDialog.Builder(getBaseActivity())
                .content("加载中...")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .build();*/
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取BaseActivity对象
     *
     * @return BaseActivity对象
     */
    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public View getFakeStatusBar() {
        return mView.findViewById(R.id.fake_status_bar);
    }

    /**
     * 请求权限
     *
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public void requestPermission(PermissionListener listener, @NonNull String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(permissions)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // All permissions are granted !
                        listener.onGranted();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        ToastUtils.showShort("权限被拒绝");
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        new QMUIDialog.MessageDialogBuilder(getBaseActivity())
                                .setMessage("权限被拒绝并设置为不再询问，请前往设置中开启")
                                .addAction("去设置", (dialog, index) -> PermissionUtils.launchAppDetailsSettings())
                                .addAction("下次再说", (dialog, index) -> dialog.dismiss())
                                .create()
                                .show();
                    }
                });
    }

    /**
     * 布局的LayoutID
     *
     * @return LayoutID
     */
    protected abstract int getLayoutID();

    /**
     * 初始化子View
     *
     * @param contentView view
     */
    protected abstract void initView(View contentView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

}
