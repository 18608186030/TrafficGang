package com.stjy.baselib.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.stjy.baselib.R;
import com.stjy.baselib.listener.PermissionListener;
import com.stjy.baselib.utils.ActivityManager;
import com.stjy.baselib.utils.EventBusUtils;
import com.stjy.baselib.utils.RxLifecycleUtils;
import com.stjy.baselib.wigiet.stateview.StateView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zhouyou.http.subsciber.IProgressDialog;

import io.reactivex.disposables.CompositeDisposable;
import me.jessyan.autosize.internal.CustomAdapt;
import me.yokeyword.fragmentation.SupportActivity;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;


/**
 * @author DaiFalin
 * @Description: Activity基类
 */
public abstract class BaseActivity extends SupportActivity implements CustomAdapt, View.OnClickListener {
    private TextView mBarTitle;
    private TextView mBarRight;
    protected Dialog mProgressDialog;
    protected Context mContext;
    protected StateView mStateView;
    protected CompositeDisposable mDisposablePool = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ARouter.getInstance().inject(this);
        ActivityManager.getInstance().addActivity(this);
        setPortraitScreen();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mStateView = StateView.inject(this, true);
        initStatusBar();
        initToolBar();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEvent()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposablePool.clear();
        if (isRegisterEvent()) {
            EventBusUtils.unregister(this);
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 设置屏幕方向
     */
    public void setPortraitScreen() {
        if (isPortraitScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选一个作为基准进行适配)
     *
     * @return {@code true} 为按照宽度适配, {@code false} 为按照高度适配
     */
    @Override
    public boolean isBaseOnWidth() {
        return isPortraitScreen();
    }

    /**
     * 返回设计图上的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return
     */
    @Override
    public float getSizeInDp() {
        return 0;
    }

    /**
     * Activity是否屏幕竖向显示
     *
     * @return true：竖向 flase：横向
     */
    public boolean isPortraitScreen() {
        return true;
    }

    /**
     * 是否订阅Bus事件
     *
     * @return
     */
    protected boolean isRegisterEvent() {
        return false;
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
        ActivityUtils.startActivity(this, toActivity);
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mBarTitle = findViewById(R.id.bar_title);
        mBarRight = findViewById(R.id.bar_right);
        //判断是否有Toolbar,并默认显示返回按钮
        if (null != getToolbar() && isShowBacking()) {
            setNavigationIcon(R.mipmap.go_back_black);
            getToolbar().setNavigationOnClickListener(v -> setNavigationOnClickListener());
        }
    }


    public void startLoadingDialog() {
        if (mProgressDialog == null) {
            initLoadingDialog();
        }
        if (mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog.show();
    }

    public void stopLoadingDialog() {
        if (mProgressDialog == null) {
            initLoadingDialog();
        }
        if (mProgressDialog.isShowing() && !this.isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    public void startLoading() {
        mStateView.showLoading();
    }

    public void stopLoading() {
        mStateView.showContent();
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
     * 设置右边的文字和颜色
     *
     * @return
     */
    public void setBarTitle(CharSequence title, @ColorInt int colorInt) {
        if (null != mBarTitle) {
            mBarTitle.setText(title);
            mBarTitle.setTextColor(colorInt);
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
     * 设置右边的文字和颜色
     *
     * @return
     */
    public void setBarRightText(CharSequence subTitle, @ColorInt int colorInt) {
        if (null != mBarRight) {
            mBarRight.setText(subTitle);
            mBarRight.setTextColor(colorInt);
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
        return findViewById(R.id.toolbar);
    }

    /**
     * 更新状态栏颜色和状态栏透明度
     */
    public void initStatusBar() {
        BarUtils.setStatusBarLightMode(this, true);
    }

    protected <T> LifecycleTransformer<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    /**
     * 请求权限
     *
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public void requestPermission(@NonNull PermissionListener listener, @NonNull String... permissions) {
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
                        new QMUIDialog.MessageDialogBuilder(this)
                                .setMessage("权限被拒绝并设置为不再询问，请前往设置中开启")
                                .addAction("去设置", (dialog, index) -> PermissionUtils.launchAppDetailsSettings())
                                .addAction("下次再说", (dialog, index) -> dialog.dismiss())
                                .create()
                                .show();
                    }
                });
    }

    /**
     * 后退按钮图片
     */
    public void setNavigationIcon(@DrawableRes int resId) {
        getToolbar().setNavigationIcon(resId);
    }

    public void setNavigationOnClickListener() {
        this.finish();
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    public boolean isShowBacking() {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化子View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    private void initLoadingDialog() {
        mProgressDialog = getProgressDialog();
    }

    public IProgressDialog getIProgressDialog() {
        return this::getProgressDialog;
    }

    public Dialog getProgressDialog() {
        return new QMUITipDialog.Builder(mContext)
                .setIconType(ICON_TYPE_LOADING)
                .setTipWord("加载中")
                .create(false);
    }
}
