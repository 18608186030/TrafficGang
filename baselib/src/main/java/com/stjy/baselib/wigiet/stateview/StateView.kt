package com.stjy.baselib.wigiet.stateview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.*
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ScrollingView
import com.coorchice.library.SuperTextView
import com.stjy.baselib.R

/**
 * @Author: superman
 * @CreateTime: 2020/12/30
 * @Describe: 状态页面工具 封装
 */
class StateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    @IntDef(RETRY, LOADING)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ViewType

    private var mRetryResource: Int
    private var mLoadingResource: Int
    private var mErrorView: View? = null
    private var mLoadingView: View? = null

    private var inflater: LayoutInflater? = null
    private var mInflateListener: OnInflateListener? = null
    private var mLayoutParamsRelative: RelativeLayout.LayoutParams? = null
    private var mLayoutParamsConstrain: ConstraintLayout.LayoutParams? = null
    private var mProvider: AnimatorProvider? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StateView)
        mRetryResource = a.getResourceId(R.styleable.StateView_emptyResource, 0)
        mLoadingResource = a.getResourceId(R.styleable.StateView_loadingResource, 0)
        a.recycle()
        if (mRetryResource == 0) {
            mRetryResource = R.layout.stateview_retry
        }
        if (mLoadingResource == 0) {
            mLoadingResource = R.layout.stateview_loading
        }
        if (attrs == null) {
            mLayoutParamsRelative = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            mLayoutParamsConstrain = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        } else {
            mLayoutParamsRelative = RelativeLayout.LayoutParams(context, attrs)
            mLayoutParamsConstrain = ConstraintLayout.LayoutParams(context, attrs)
        }
        visibility = GONE
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

    override fun dispatchDraw(canvas: Canvas) {}

    override fun setVisibility(visibility: Int) {
        setVisibility(mErrorView, visibility)
        setVisibility(mLoadingView, visibility)
    }

    private fun setVisibility(view: View?, visibility: Int) {
        if (view != null && visibility != view.visibility) {
            if (mProvider != null) {
                startAnimation(view)
            } else {
                view.visibility = visibility
            }
        }
    }

    /**
     * 显示内容视图
     */
    fun showContent() {
        visibility = GONE
    }

    /**
     * 显示重试加载视图
     *
     * @param retryTitle 标题
     * @param retryDrawableResId 顶部图片
     * @param retryBtnText 按钮文字
     * @param mRetryClickListener 按钮回调
     * @return mRetryView
     */
    fun showRetry(retryTitle: String? = null, retryDrawableResId: Int? = null, retryBtnText: String? = null, mRetryClickListener: OnClickListener? = null): View? {
        return mErrorView ?: with(inflate(mRetryResource, RETRY)) {
            mErrorView = this
            this.findViewById<ImageView>(R.id.iv_image)?.setImageResource(retryDrawableResId
                    ?: R.mipmap.ic_stateview_empty)
            this.findViewById<TextView>(R.id.tv_title)?.text = retryTitle ?: "似乎出了点问题..."
            this.findViewById<SuperTextView>(R.id.btn_retry)?.text = retryBtnText ?: "重新加载"
            this.findViewById<SuperTextView>(R.id.btn_retry)?.setOnClickListener(mRetryClickListener)
            showView(this)
            this
        }
    }

    /**
     * 显示加载中布局
     *
     * @return
     */
    fun showLoading(): View {
        return mLoadingView ?: with(inflate(mLoadingResource, LOADING)) {
            mLoadingView = this
            showView(this)
            this
        }
    }

    /**
     * show the state view
     */
    private fun showView(view: View?) {
        setVisibility(view, VISIBLE)
        hideViews(view)
    }

    /**
     * hide other views after show view
     */
    private fun hideViews(showView: View?) {
        when {
            mLoadingView === showView -> {
                setVisibility(mErrorView, GONE)
            }
            else -> {
                setVisibility(mLoadingView, GONE)
            }
        }
    }

    private fun startAnimation(view: View) {
        val toShow = view.visibility == GONE
        val animator = if (toShow) mProvider?.showAnimation(view) else mProvider?.hideAnimation(view)
        if (animator == null) {
            view.visibility = if (toShow) VISIBLE else GONE
            return
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (!toShow) {
                    view.visibility = GONE
                }
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                if (toShow) {
                    view.visibility = VISIBLE
                }
            }
        })
        animator.start()
    }

    /**
     * provider default is null
     *
     * @param provider [AnimatorProvider]
     */
    fun setAnimatorProvider(provider: AnimatorProvider?) {
        mProvider = provider
        reset(mLoadingView)
        reset(mErrorView)
    }

    /**
     * reset view's property
     * 不然多次 setAnimatorProvider 后视图动画会混乱
     */
    private fun reset(view: View?) {
        if (view != null) {
            view.translationX = 0f
            view.translationY = 0f
            view.alpha = 1f
            view.rotation = 0f
            view.scaleX = 1f
            view.scaleY = 1f
        }
    }

    private fun inflate(@LayoutRes layoutResource: Int, @ViewType viewType: Int): View {
        val viewParent = parent
        return if (viewParent != null && viewParent is ViewGroup) {
            if (layoutResource != 0) {
                val factory: LayoutInflater = inflater ?: LayoutInflater.from(context)
                val view = factory.inflate(layoutResource, viewParent, false)
                val index = viewParent.indexOfChild(this)
                // 防止还能触摸底下的 View
                view.isClickable = true
                // 先不显示
                view.visibility = GONE
                val layoutParams = layoutParams
                if (layoutParams != null) {
                    when (viewParent) {
                        is RelativeLayout -> {
                            val lp = layoutParams as MarginLayoutParams
                            mLayoutParamsRelative?.setMargins(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
                            viewParent.addView(view, index, mLayoutParamsRelative)
                        }
                        is ConstraintLayout -> {
                            viewParent.addView(view, index, mLayoutParamsConstrain)
                        }
                        else -> {
                            viewParent.addView(view, index, layoutParams)
                        }
                    }
                } else {
                    viewParent.addView(view, index)
                }
                if (mLoadingView != null && mErrorView != null) {
                    viewParent.removeViewInLayout(this)
                }
                mInflateListener?.onInflate(viewType, view)
                view
            } else {
                throw IllegalArgumentException("StateView must have a valid layoutResource")
            }
        } else {
            throw IllegalStateException("StateView must have a non-null ViewGroup viewParent")
        }
    }

    /**
     * 设置 topMargin, 当有 actionbar/toolbar 的时候
     */
    fun setTopMargin() {
        val layoutParams = layoutParams as MarginLayoutParams
        layoutParams.topMargin = actionBarHeight
    }

    /**
     * @return actionBarSize
     */
    val actionBarHeight: Int
        get() {
            var height = 0
            val tv = TypedValue()
            if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
                height = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            }
            return height
        }

    /**
     * Specifies the inflate listener to be notified after this StateView successfully
     * inflated its layout resource.
     * @param inflateListener The OnInflateListener to notify of successful inflation.
     * @see OnInflateListener
     */
    fun setOnInflateListener(inflateListener: OnInflateListener?) {
        mInflateListener = inflateListener
    }

    /**
     * Listener used to receive a notification after a StateView has successfully
     * inflated its layout resource.
     * @see StateView.setOnInflateListener
     */
    interface OnInflateListener {
        /**
         * @param view The inflated View.
         */
        fun onInflate(@ViewType viewType: Int, view: View?)
    }

    companion object {
        const val RETRY = 0x00000001
        const val LOADING = 0x00000002

        /**
         * 注入到 activity 中
         *
         * @param activity     Activity
         * @param hasActionBar 是否有 actionbar/toolbar
         * @return StateView
         */
        @JvmOverloads
        fun inject(activity: Activity, hasActionBar: Boolean = false): StateView {
            return inject(activity.window.decorView.findViewById<View>(android.R.id.content) as ViewGroup, hasActionBar)
        }

        /**
         * 注入到 ViewGroup 中
         *
         * @param parent       extends ViewGroup
         * @param hasActionBar 是否有 actionbar/toolbar,
         * true: 会 setMargin top, margin 大小是 actionbarSize
         * false: not set
         * @return StateView
         */
        @JvmOverloads
        fun inject(parent: ViewGroup, hasActionBar: Boolean = false): StateView {
            // 因为 LinearLayout/ScrollView/AdapterView 的特性
            // 为了 StateView 能正常显示，自动再套一层（开发的时候就不用额外的工作量了）
            // SwipeRefreshLayout/NestedScrollView
            // If there are other complex needs, maybe you can use stateView in layout(.xml)
            var parent = parent
            var screenHeight = 0
            if (parent is LinearLayout ||
                    parent is ScrollView ||
                    parent is AdapterView<*> ||
                    parent is ScrollingView && parent is NestedScrollingChild ||
                    parent is NestedScrollingParent && parent is NestedScrollingChild) {
                val viewParent = parent.parent
                if (viewParent == null) {
                    // create a new FrameLayout to wrap StateView and parent's childView
                    val wrapper = FrameLayout(parent.context)
                    val layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    wrapper.layoutParams = layoutParams
                    if (parent is LinearLayout) {
                        // create a new LinearLayout to wrap parent's childView
                        val wrapLayout = LinearLayout(parent.getContext())
                        wrapLayout.layoutParams = parent.getLayoutParams()
                        wrapLayout.orientation = parent.orientation
                        var i = 0
                        val childCount = parent.getChildCount()
                        while (i < childCount) {
                            val childView = parent.getChildAt(0)
                            parent.removeView(childView)
                            wrapLayout.addView(childView)
                            i++
                        }
                        wrapper.addView(wrapLayout)
                    } else if (parent is ScrollView || parent is ScrollingView) {
                        // not recommended to inject Scrollview/NestedScrollView
                        check(parent.childCount == 1) { "the ScrollView does not have one direct child" }
                        val directView = parent.getChildAt(0)
                        parent.removeView(directView)
                        wrapper.addView(directView)
                        val wm = parent.context
                                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                        val metrics = DisplayMetrics()
                        wm.defaultDisplay.getMetrics(metrics)
                        screenHeight = metrics.heightPixels
                    } else if (parent is NestedScrollingParent &&
                            parent is NestedScrollingChild) {
                        if (parent.childCount == 2) {
                            val targetView = parent.getChildAt(1)
                            parent.removeView(targetView)
                            wrapper.addView(targetView)
                        } else check(parent.childCount <= 2) {
                            ("the view is not refresh layout? view = "
                                    + parent.toString())
                        }
                    } else {
                        throw IllegalStateException("the view does not have parent, view = "
                                + parent.toString())
                    }
                    // parent add wrapper
                    parent.addView(wrapper)
                    // StateView will be added to wrapper
                    parent = wrapper
                } else {
                    val root = FrameLayout(parent.context)
                    root.layoutParams = parent.layoutParams
                    if (viewParent is ViewGroup) {
                        val rootGroup = viewParent
                        // 把 parent 从它自己的父容器中移除
                        rootGroup.removeView(parent)
                        // 然后替换成新的
                        rootGroup.addView(root)
                        Injector.changeChildrenConstraints(rootGroup, root, parent.id)
                    }

                    // if viewParent is ConstraintLayout, setLayoutParams must after rootGroup.removeView(parent);
                    // @see at android.support.constraint.ConstraintLayout.getViewWidget
                    // @see at android.support.constraint.ConstraintLayout.onViewRemoved
                    val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    parent.layoutParams = layoutParams
                    root.addView(parent)
                    parent = root
                }
            }
            val stateView = StateView(parent.context)
            if (screenHeight > 0) {
                // let StateView be shown in the center
                parent.addView(stateView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, if (hasActionBar) screenHeight - stateView.actionBarHeight else screenHeight))
            } else {
                parent.addView(stateView)
            }
            if (hasActionBar) {
                stateView.setTopMargin()
            }
            stateView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            stateView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            return stateView
        }

        /**
         * @param view         instanceof ViewGroup
         * @param hasActionBar 是否有 actionbar/toolbar
         * @return StateView
         * @Describe StateView  注入到 View 中
         */
        @JvmOverloads
        fun inject(view: View, hasActionBar: Boolean = false): StateView {
            return if (view is ViewGroup) {
                inject(view, hasActionBar)
            } else {
                val parent = view.parent
                if (parent is ViewGroup) {
                    inject(parent, hasActionBar)
                } else {
                    throw ClassCastException("view or view.getParent() must be ViewGroup")
                }
            }
        }

        /**
         * 包裹 view
         *
         * @param view target view
         * @return StateView
         */
        fun wrap(view: View): StateView {
            val stateView = StateView(view.context)
            with(view.parent as ViewGroup) {
                this.removeView(view)
                this.addView(with(FrameLayout(view.context)) {
                    this.addView(view)
                    this.addView(stateView, view.layoutParams)
                    this
                })
            }
            return stateView
        }
    }

    /**
     * @author Nukc.
     */
    interface AnimatorProvider {
        fun showAnimation(view: View?): Animator?
        fun hideAnimation(view: View?): Animator?
    }

    internal object Injector {
        fun changeChildrenConstraints(viewParent: ViewGroup?, root: FrameLayout, injectViewId: Int) {
            if (viewParent is ConstraintLayout) {
                val rootId = R.id.root_id
                root.id = rootId
                val rootGroup = viewParent
                var i = 0
                val count = rootGroup.childCount
                while (i < count) {
                    val child = rootGroup.getChildAt(i)
                    val layoutParams = child.layoutParams as ConstraintLayout.LayoutParams
                    if (layoutParams.circleConstraint == injectViewId) {
                        layoutParams.circleConstraint = rootId
                    } else {
                        if (layoutParams.leftToLeft == injectViewId) {
                            layoutParams.leftToLeft = rootId
                        } else if (layoutParams.leftToRight == injectViewId) {
                            layoutParams.leftToRight = rootId
                        }
                        if (layoutParams.rightToLeft == injectViewId) {
                            layoutParams.rightToLeft = rootId
                        } else if (layoutParams.rightToRight == injectViewId) {
                            layoutParams.rightToRight = rootId
                        }
                        if (layoutParams.topToTop == injectViewId) {
                            layoutParams.topToTop = rootId
                        } else if (layoutParams.topToBottom == injectViewId) {
                            layoutParams.topToBottom = rootId
                        }
                        if (layoutParams.bottomToTop == injectViewId) {
                            layoutParams.bottomToTop = rootId
                        } else if (layoutParams.bottomToBottom == injectViewId) {
                            layoutParams.bottomToBottom = rootId
                        }
                        if (layoutParams.baselineToBaseline == injectViewId) {
                            layoutParams.baselineToBaseline = rootId
                        }
                    }
                    i++
                }
            }
        }
    }
}