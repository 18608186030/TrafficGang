package com.stjy.baselib.base.mvc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.stjy.baselib.R
import com.stjy.baselib.listener.PermissionListener
import com.stjy.baselib.utils.EventBusUtils
import com.stjy.baselib.utils.RxLifecycleUtils
import com.stjy.baselib.wigiet.stateview.StateView
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.disposables.CompositeDisposable
import me.yokeyword.fragmentation.SupportFragment

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: BaseFragment基类
 */
abstract class BaseFragment : SupportFragment(), View.OnClickListener {
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
        mView = with(inflater.inflate(getLayoutID(), container, false)){
            mStateView = StateView.inject(this)
            this
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        try {
            if (isVisibleToUser) {
                initStatusBar()
            }
        } catch (e: Exception) {

        }
    }

    /**
     * 更新状态栏颜色和状态栏透明度
     */
    open fun initStatusBar() {
       mActivity.initStatusBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRegisterEvent()) {
            EventBusUtils.unregister(this)
        }
        mActivity.mProgressDialog?.dismiss()
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

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        val fragment = parentFragment
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else {
            super.startActivityForResult(intent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        childFragmentManager.fragments?.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    open fun startLoadingDialog() {
        mActivity?.startLoadingDialog()
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
    fun requestPermission(@NonNull listener: PermissionListener,@NonNull vararg permissions: String) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.requestEachCombined(*permissions)
                .subscribe { permission: Permission ->
                    when {
                        permission.granted -> listener.onGranted()
                        permission.shouldShowRequestPermissionRationale -> ToastUtils.showShort("权限被拒绝")
                        else -> {
                            MessageDialogBuilder(mActivity)
                                    .setMessage("权限被拒绝并设置为不再询问，请前往设置中开启")
                                    .addAction("去设置") { dialog: QMUIDialog?, index: Int -> PermissionUtils.launchAppDetailsSettings() }
                                    .addAction("下次再说") { dialog: QMUIDialog, index: Int -> dialog.dismiss() }
                                    .create()
                                    .show()
                        }
                    }
                }
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