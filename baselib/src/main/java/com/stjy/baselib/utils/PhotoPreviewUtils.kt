package com.stjy.baselib.utils

import android.content.Context
import com.stjy.baselib.wigiet.photopreview.activity.PhotoPreviewActivity

/**
 * Created by 代发琳 on 17-9-20.
 * 图片预览工具类
 * function：
 */
object PhotoPreviewUtils {
    /**
     * 跳转图片预览
     */
    fun start(context: Context, position: Int, images: List<String?>?) {
        PhotoPreviewActivity.start(context,position,images)
    }

    /**
     * 跳转图片预览
     */
    fun start(context: Context, position: Int, image: String?) {
        PhotoPreviewActivity.start(context,position,image)
    }
}