package com.stjy.baselib.wigiet.photopreview.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import com.blankj.utilcode.util.BarUtils
import com.stjy.baselib.R
import com.stjy.baselib.base.mvc.BaseActivity
import com.stjy.baselib.wigiet.photopreview.adapter.PhotoPagerAdapter
import com.stjy.baselib.wigiet.photopreview.fragment.PhotoFragment.Companion.newInstance
import java.util.*

/**
 * @author acb
 */
class PhotoPreviewActivity : BaseActivity() {
    private lateinit var images: List<String>
    private lateinit var vpPhoto: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)
    }

    public override fun initData() {}
    override fun initStatusBar() {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.black))
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar!!)
        BarUtils.setStatusBarLightMode(this, false)
    }

    public override fun initView() {
        val bundle = intent.extras
        images = bundle.getStringArrayList(IMAGES)?: arrayListOf()
        toolbar?.setBackgroundResource(R.color.black)
        vpPhoto = findViewById(R.id.vpPhoto)

        val fragments: MutableList<Fragment> = ArrayList()
        for (i in images.indices) {
            fragments.add(newInstance(images[i]))
        }
        val photoPagerAdapter = PhotoPagerAdapter(supportFragmentManager, fragments)
        vpPhoto.adapter = photoPagerAdapter
        vpPhoto.currentItem = bundle.getInt(POSITION)
        setBarTitle("${vpPhoto.currentItem + 1}/${images.size}", ContextCompat.getColor(this, R.color.white))
    }

    public override fun initListener() {
        vpPhoto.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                setBarTitle("${vpPhoto.currentItem + 1}/${images.size}")
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    companion object {
        const val POSITION = "BASICRES_POSITION"
        const val IMAGES = "BASICRES_IMAGE"

        /**
         * @param context
         * @param position 当前图片位置
         * @param images   图片集合
         */
        fun start(context: Context, position: Int, images: List<String?>?) {
            context.startActivity(with(Intent(context, PhotoPreviewActivity::class.java)){
                this.putExtras(with(Bundle()){
                    this.putInt(POSITION, position)
                    this.putStringArrayList(IMAGES, images as ArrayList<String?>?)
                    this
                })
                this
            })
        }

        /**
         * @param context
         * @param position 当前图片位置
         * @param image   图片路径或url
         */
        fun start(context: Context, position: Int, image: String?) {
            context.startActivity(with(Intent(context, PhotoPreviewActivity::class.java)){
                this.putExtras(with(Bundle()){
                    this.putInt(POSITION, position)
                    this.putStringArrayList(IMAGES, object : ArrayList<String?>() {
                        init {
                            add(image)
                        }
                    })
                    this
                })
                this
            })
        }
    }
}