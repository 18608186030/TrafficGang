package com.stjy.baselib.base.mvc

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.stjy.baselib.R
import com.stjy.baselib.utils.EventBusUtils
import com.stjy.baselib.utils.RxLifecycleUtils
import com.stjy.baselib.wigiet.loading.LoadingDialog
import com.stjy.baselib.wigiet.stateview.StateView
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import me.jessyan.autosize.internal.CustomAdapt
import me.yokeyword.fragmentation.SupportActivity

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: Activity基类
 */
abstract class BaseActivity : SupportActivity(), CustomAdapt, View.OnClickListener , LifecycleProvider<ActivityEvent> {
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    private lateinit var rxPermissions: RxPermissions
    var mLoadingDialog: LoadingDialog? = null
    var mStateView: StateView? = null
    var mDisposablePool = CompositeDisposable()
    private var mBarTitle: TextView? = null
    private var mBarRight: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
        setPortraitScreen()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        if (isRegisterEvent()) {
            EventBusUtils.register(this)
        }
        mStateView = StateView.inject(this, true)
        initImmersionBar()//初始化沉浸式
        initToolBar()
        initView()
        initListener()
        initData()
    }

    @CallSuper
    override fun onStart() {
        lifecycleSubject.onNext(ActivityEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME)
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
        mDisposablePool.clear()
        if (isRegisterEvent()) {
            EventBusUtils.unregister(this)
        }
        mLoadingDialog?.dismiss()
    }

    @NonNull
    @CheckResult
    override fun lifecycle() = lifecycleSubject.hide()

    @NonNull
    @CheckResult
    override fun <T : Any> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @NonNull
    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycleAndroid.bindActivity(lifecycleSubject)

    /**
     * 设置屏幕方向
     */
    open fun setPortraitScreen() {
        requestedOrientation = if (isPortraitScreen) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    /**
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选一个作为基准进行适配)
     * @return `true` 为按照宽度适配, `false` 为按照高度适配
     */
    override fun isBaseOnWidth(): Boolean {
        return isPortraitScreen
    }

    /**
     * 返回设计图上的设计尺寸, 单位 dp
     * [.getSizeInDp] 须配合 [.isBaseOnWidth] 使用, 规则如下:
     * 如果 [.isBaseOnWidth] 返回 `true`, [.getSizeInDp] 则应该返回设计图的总宽度
     * 如果 [.isBaseOnWidth] 返回 `false`, [.getSizeInDp] 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, [.getSizeInDp] 则返回 `0`
     * @return
     */
    override fun getSizeInDp(): Float {
        return 0.toFloat()
    }

    /**
     * Activity是否屏幕竖向显示
     * @return true：竖向 flase：横向
     */
    val isPortraitScreen: Boolean
        get() = true

    /**
     * 是否订阅Bus事件
     * @return
     */
    open fun isRegisterEvent(): Boolean = false

    /**
     * 打开新的Activity
     * @param toActivity 新Activity
     * @param isFinish   是否关闭当前Activity
     */
    @JvmOverloads
    fun startActivity(toActivity: Class<out AppCompatActivity?>, isFinish: Boolean = false) {
        ActivityUtils.startActivity(this, toActivity)
        if (isFinish) {
            finish()
        }
    }

    /**
     * 初始化ToolBar
     */
    private fun initToolBar() {
        mBarTitle = findViewById(R.id.bar_title)
        mBarRight = findViewById(R.id.bar_right)
        //判断是否有Toolbar,并默认显示返回按钮
        toolbar?.let {
            it.setBackgroundResource(R.drawable.shap_toolbar_bg)
            if (isShowBacking()) {
                setNavigationIcon(R.mipmap.ic_black)
                it.setNavigationOnClickListener { setNavigationOnClickListener() }
            }
        }
    }

    fun startLoadingDialog(content: CharSequence? = null,
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
        mLoadingDialog = mLoadingDialog ?: LoadingDialog(this)
        mLoadingDialog?.showing(content,
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

    fun stopLoadingDialog() {
        mLoadingDialog?.dismissing()
    }

    fun startLoading() {
        mStateView?.showLoading()
    }

    fun stopLoading() {
        mStateView?.showContent()
    }

    /**
     * 设置头部标题
     * @param title
     */
    fun setBarTitle(title: CharSequence?) {
        mBarTitle?.text = title
    }

    /**
     * 设置右边的文字和颜色
     * @return
     */
    fun setBarTitle(title: CharSequence?, @ColorInt colorInt: Int) {
        mBarTitle?.text = title
        mBarTitle?.setTextColor(colorInt)
    }

    /**
     * 设置右边的文字
     * @return
     */
    fun setBarRightText(subTitle: CharSequence?) {
        mBarRight?.text = subTitle
    }

    /**
     * 设置右边的文字和颜色
     * @return
     */
    fun setBarRightText(subTitle: CharSequence?, @ColorInt colorInt: Int) {
        mBarRight?.text = subTitle
        mBarRight?.setTextColor(colorInt)
    }

    /**
     * 设置右边的文字的点击监听
     * @param listener
     */
    fun setOnClickRightTextListener(listener: View.OnClickListener?) {
        if (null != mBarRight && null != listener) {
            mBarRight?.setOnClickListener(listener)
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部
     * @return support.v7.widget.Toolbar.
     */
    val toolbar: Toolbar?
        get() = findViewById(R.id.toolbar)

    protected fun <T> bindLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleUtils.bindLifecycle(this)
    }

    /**
     * 请求权限
     * @param permissions
     */
    @SuppressLint("CheckResult")
    fun requestPermission(vararg permissions: String, success: (() -> Unit)? = null, failed: (() -> Unit)? = null) {
        rxPermissions.requestEachCombined(*permissions)
                .subscribe { permission: Permission ->
                    when {
                        //请求的权限全部都通过
                        permission.granted -> success?.let { it() }
                        //请求的权限至少有一条被决绝了
                        permission.shouldShowRequestPermissionRationale -> failed?.let { it() }
                        else -> {
                            //请求的权限至少有一条被永久拒绝的权限（拒绝不在提醒）
                            failed?.let { it() }
                            //startActivity(with(Intent()) {
                            //    this.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            //    this.data = Uri.parse("package:" + this@CommonBaseActivity.packageName)
                            //    this
                            //})

                            //MessageDialogBuilder(this)
                            //        .setMessage("权限被拒绝并设置为不再询问，请前往设置中开启")
                            //        .addAction("去设置") { dialog: QMUIDialog?, index: Int -> PermissionUtils.launchAppDetailsSettings() }
                            //        .addAction("下次再说") { dialog: QMUIDialog, index: Int -> dialog.dismiss() }
                            //        .create()
                            //        .show()
                        }
                    }
                }
    }

    /**
     * 后退按钮图片
     */
    fun setNavigationIcon(@DrawableRes resId: Int) {
        toolbar?.setNavigationIcon(resId)
    }

    open fun setNavigationOnClickListener() {
        finish()
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     * @return
     */
    open fun isShowBacking(): Boolean = true

    override fun onClick(v: View) {}

    /**
     * 初始化子View
     */
    protected abstract fun initView()

    /**
     * 初始化监听器
     */
    protected abstract fun initListener()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    open fun initImmersionBar() {
        immersionBar {
            statusBarColor(R.color.white)
            navigationBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
    }
}