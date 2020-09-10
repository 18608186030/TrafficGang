package com.stjy.baselib.wigiet

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import java.util.*
import android.graphics.Color
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils

// 该控件为地图导航栏图标的载体
class BottomView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var vp: ViewPager? = null
    private var bottomPageChangeListener: BottomPageChangeListener? = null

    // 同tablayout用法相似，与ViewPager进行绑定
    fun setViewPager(viewPager: ViewPager, botBeans: ArrayList<BotBean>, bottomPageChangeListener: BottomPageChangeListener) {
        vp = viewPager
        this.bottomPageChangeListener = bottomPageChangeListener
        initTabView(botBeans)

        // 设置viewPager的点击事件
        vp?.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until childCount) { // 设置被选择的为true
                    getChildAt(i).isSelected = position == i
                }
                bottomPageChangeListener?.onBottomPageChangeListener(position)
            }
        })
    }

    // 初始化底部导航栏，viewpager有几页，就创建几个图标
    private fun initTabView(botBeans: ArrayList<BotBean>) {
        gravity = HORIZONTAL // 设置为水平
        for (i in botBeans.indices) {
            val bean = botBeans[i]
            val tabView = TabView(context, bean)
            val params = LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            params.gravity = Gravity.CENTER
            tabView.layoutParams = params

            // 为每个view设置点击事件,点击view就会跳到相应的页面上
            tabView.setOnClickListener {
                vp?.setCurrentItem(i, false) // false为取消滑动效果
            }
            // 设置第一个view一开始就是选中状态
            if (i == 0) {
                tabView.isSelected = true
                // 因为初始化时，onPageSelected没有得到执行，所以主动去调用回调方法
                bottomPageChangeListener?.onBottomPageChangeListener(i)
            }
            addView(tabView)
        }
    }

    // 提供接口回调，每次滑动通知外界
    interface BottomPageChangeListener {
        fun onBottomPageChangeListener(position: Int)
    }
}

// 图标 + 文字的 底部按钮
class TabView(context: Context, private var botBean: BotBean) : LinearLayout(context) {
    private lateinit var iconName: TextView
    private lateinit var iconImage: ImageView

    init {
        initView()
    }

    private fun initView() {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        // 添加小图标
        iconImage = ImageView(context)
        val layoutParams = LayoutParams(SizeUtils.dp2px(25f), SizeUtils.dp2px(25f)) // 定义布局的宽高
        iconImage.layoutParams = layoutParams // 设置图标的大小
        iconImage.setImageResource(botBean.unCheckedIcon) // 获取图片

        // 设置的图标为灰色
        val drawable = context.resources.getDrawable(botBean.unCheckedIcon)
        val wrapDrawable = DrawableCompat.wrap(drawable)
        //DrawableCompat.setTintList(wrapDrawable, ColorStateList.valueOf(Color.GRAY));
        iconImage.setImageDrawable(wrapDrawable)
        addView(iconImage) // 添加图标图片

        // 设置图标标题并添加
        iconName = TextView(context)
        val titleParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        iconName.layoutParams = titleParams
        iconName.text = botBean.iconName
        addView(iconName)
    }

    // 判断选择状态改变图标，供外部调用
    override fun setSelected(isSelected: Boolean) {
        if (isSelected) {
            // 使用颜色过滤器，改变选中时的颜色为红色
            val drawable = context.resources.getDrawable(botBean.checkedIcon)
            val wrapDrawable = DrawableCompat.wrap(drawable)
            //DrawableCompat.setTintList(wrapDrawable,ColorStateList.valueOf(Color.BLACK));
            iconImage.setImageDrawable(wrapDrawable)
            iconName.setTextColor(Color.BLACK)
        } else {
            // 没选中的图标为黑色，标题为灰色
            val drawable = context.resources.getDrawable(botBean.unCheckedIcon)
            val wrapDrawable = DrawableCompat.wrap(drawable)
            //DrawableCompat.setTintList(wrapDrawable,ColorStateList.valueOf(Color.GRAY));
            iconImage.setImageDrawable(wrapDrawable)
            iconName.setTextColor(Color.GRAY)
        }
    }
}

data class BotBean(// 导航栏图标名字
        var iconName: String, // 未选中的图标
        var unCheckedIcon: Int, // 未选中的图标
        var checkedIcon: Int)