package com.stjy.baselib.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.stjy.baselib.wigiet.photopreview.activity.PhotoPreviewActivity
import java.util.*

/**
 * Created by 代发琳 on 17-9-20.
 * 图片预览工具类
 * function：
 */
object PhotoPreviewUtils {
    const val POSITION = "BASICRES_POSITION"
    const val IMAGES = "BASICRES_IMAGE"

    /**
     * 跳转图片预览
     */
    fun start(context: Context, position: Int, images: List<String?>?) {
        val intent = Intent(context, PhotoPreviewActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        bundle.putStringArrayList(IMAGES, images as ArrayList<String?>?)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    /**
     * 跳转图片预览
     */
    fun start(context: Context, position: Int, image: String?) {
        val intent = Intent(context, PhotoPreviewActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        bundle.putStringArrayList(IMAGES, object : ArrayList<String?>() {
            init {
                add(image)
            }
        })
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}