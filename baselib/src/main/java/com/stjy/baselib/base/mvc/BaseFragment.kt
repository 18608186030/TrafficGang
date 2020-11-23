package com.stjy.baselib.base.mvc

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.stjy.baselib.R
import com.stjy.baselib.utils.EventBusUtils
import com.stjy.baselib.utils.RxLifecycleUtils
import com.stjy.baselib.wigiet.stateview.StateView
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.disposables.CompositeDisposable
import me.yokeyword.fragmentation.SupportFragment


/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: BaseFragment基类
 */
abstract class BaseFragment : SupportFragment(), SimpleImmersionOwner, View.OnClickListener {
    /**
     * ImmersionBar代理类
     */
    private var mSimpleImmersionProxy=SimpleImmersionProxy(this)
    lateinit var mActivity: BaseActivity
    private var mView: View? = null
    protected lateinit var mStateView: StateView
    var mDisposablePool = CompositeDisposable()
    private var mBarTitle: TextView? = null
    private var mBarRight: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        mActivity = activity as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = with(inflater.inflate(getLayoutID(), container, false)) {
            mStateView = StateView.inject(this)
            this
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
        if (isRegisterEvent()) {
            EventBusUtils.register(this)
        }
        initToolBar()
        initView(mView)
        initListener()
        initData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 初始化沉浸式代码
     * Init immersion bar.
     */
    override fun initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                .statusBarDarkFont(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .init()
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled() = true

    override fun onDestroyView() {
        super.onDestroyView()
        mSimpleImmersionProxy.onDestroy()
        if (isRegisterEvent()) {
            EventBusUtils.unregister(this)
        }
        mActivity.mLoadingDialog?.dismissing()
        mDisposablePool.clear()
    }

    /**
     * 是否订阅Bus事件
     *
     * @return
     */
    open fun isRegisterEvent(): Boolean = false

    /**
     * 初始化ToolBar
     */
    private fun initToolBar() {
        mBarTitle = mView?.findViewById(R.id.bar_title)
        mBarRight = mView?.findViewById(R.id.bar_right)
        //判断是否有Toolbar,并默认显示返回按钮
        toolbar?.let {
            initImmersionBar()
            if (isShowBacking()) {
                setNavigationIcon(R.mipmap.ic_black)
                it.setNavigationOnClickListener { setNavigationOnClickListener() }
            }
        }
    }

    open fun setNavigationOnClickListener() {
        mActivity.finish()
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    fun setBarTitle(title: CharSequence?) {
        mBarTitle?.text = title
    }

    /**
     * 设置右边的文字
     *
     * @return
     */
    fun setBarRightText(subTitle: CharSequence?) {
        mBarRight?.text = subTitle
    }

    /**
     * 设置右边的文字的点击监听
     *
     * @param listener
     */
    fun setOnClickRightTextListener(listener: View.OnClickListener?) {
        if (null != listener) {
            mBarRight?.setOnClickListener(listener)
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    val toolbar: Toolbar?
        get() = mView?.findViewById(R.id.toolbar)

    protected fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleUtils.bindLifecycle(this)
    }

    /**
     * 后退按钮图片
     */
    open fun setNavigationIcon(@DrawableRes resId: Int) {
        toolbar?.setNavigationIcon(resId)
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    open fun isShowBacking(): Boolean = true

    /**
     * 打开新的Activity
     *
     * @param toActivity 新Activity
     * @param isFinish   是否关闭当前Activity
     */
    @JvmOverloads
    fun startActivity(toActivity: Class<out AppCompatActivity?>, isFinish: Boolean = false) {
        ActivityUtils.startActivity(mActivity, toActivity)
        if (isFinish) {
            mActivity.finish()
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        val fragment = parentFragment
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else {
            super.startActivityForResult(intent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragmentList = childFragmentManager.fragments
        if (fragmentList != null) {
            for (fragment in fragmentList) {
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    open fun startLoadingDialog(content: CharSequence? = null,
                                contentTextColor: Int? = null,
                                contentTextSize: Int? = null,
                                cancelable: Boolean? = null,
                                canceledOnTouchOutside: Boolean? = null,
                                strokeWidth: Int? = null,
                                strokeColor: Int? = null,
                                solidColor: Int? = null,
                                cancelListener: DialogInterface.OnCancelListener? = null,
                                cornerRadius: Int? = null,
                                LoadingViewSize: Int? = null,
                                dimAmount: Float? = null) {
        mActivity?.startLoadingDialog(content,
                contentTextColor,
                contentTextSize,
                cancelable,
                canceledOnTouchOutside,
                strokeWidth,
                strokeColor,
                solidColor,
                cancelListener,
                cornerRadius,
                LoadingViewSize,
                dimAmount)
    }

    open fun stopLoadingDialog() {
        mActivity?.stopLoadingDialog()
    }

    fun startLoading() {
        mStateView?.showLoading()
    }

    fun stopLoading() {
        mStateView?.showContent()
    }

    override fun onClick(v: View) {}

    /**
     * 请求权限
     *
     * @param permissions
     */
    @SuppressLint("CheckResult")
    fun requestPermission(vararg permissions: String, success: (() -> Unit)? = null, failed: (() -> Unit)? = null) {
        mActivity?.requestPermission(*permissions, success = success, failed = failed)
    }

    /**
     * 布局的LayoutID
     *
     * @return LayoutID
     */
    protected abstract fun getLayoutID(): Int

    /**
     * 初始化子View
     *
     * @param contentView view
     */
    protected abstract fun initView(contentView: View?)

    /**
     *
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     *
     * 初始化监听器
     */
    protected abstract fun initListener()

    /**
     *
     *扩展点击事件
     */
    fun View.onClick(listener: View.OnClickListener): View {
        setOnClickListener(listener)
        return this
    }

    /**
     *
     *扩展点击事件，参数为方法
     */
    fun View.onClick(method: () -> Unit): View {
        setOnClickListener { method() }
        return this
    }

    /**
     *
     *扩展视图可见性
     */
    fun View.setVisible(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }
}