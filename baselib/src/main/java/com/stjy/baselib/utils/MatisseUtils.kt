package com.stjy.baselib.utils

import android.Manifest
import android.app.Activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.AppUtils
import com.stjy.baselib.R
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.SelectionCreator
import com.zhihu.matisse.internal.entity.CaptureStrategy

/**
 * @author daifalin
 * @date 2019/3/18 10:42 AM
 * @ClassName MatisseUtils
 * @Description
 */
object MatisseUtils {
    const val CHOOSE_REQUEST = 222
    val PERMISSION = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val AUTHORITY = AppUtils.getAppPackageName() + ".matisseFileProvider"
    private val CAPTURE_STRATEGY = CaptureStrategy(true, AUTHORITY, "Camera")

    /**
     * 选择图片
     *
     * @param activity Activity instance.
     * @return
     */
    fun fromImage(activity: Activity): SelectionCreator {
        return matisseFrom(activity, MimeType.ofImage(), true)
    }

    /**
     * 选择图片
     *
     * @param fragment Fragment instance.
     * @return
     */
    fun fromImage(fragment:Fragment): SelectionCreator {
        return matisseFrom(fragment, MimeType.ofImage(), true)
    }

    /**
     * 选择视频
     *
     * @param activity Activity instance.
     * @return
     */
    fun fromVideo(activity: Activity): SelectionCreator {
        return matisseFrom(activity, MimeType.ofVideo(), true).capture(false)
    }

    /**
     * 选择视频
     *
     * @param fragment Fragment instance.
     * @return
     */
    fun fromVideo(fragment: Fragment): SelectionCreator {
        return matisseFrom(fragment, MimeType.ofVideo(), true).capture(false)
    }

    /**
     * 选择全部
     *
     * @param activity Activity instance.
     * @return
     */
    fun fromAll(activity: Activity): SelectionCreator {
        return matisseFrom(activity, MimeType.ofAll(), false)
    }

    /**
     * 选择全部
     *
     * @param fragment Fragment instance.
     * @return
     */
    fun fromAll(fragment: Fragment): SelectionCreator {
        return matisseFrom(fragment, MimeType.ofAll(), false)
    }

    private fun matisseFrom(fragment:Fragment, mimeTypes: Set<MimeType>, showSingleMediaType: Boolean): SelectionCreator {
        return Matisse.from(fragment)
                .choose(mimeTypes)
                .countable(true)
                .capture(true)
                .showSingleMediaType(showSingleMediaType)
                .captureStrategy(CAPTURE_STRATEGY)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dracula)
                .imageEngine(Glide4Engine())
    }

    private fun matisseFrom(activity: Activity, mimeTypes: Set<MimeType>, showSingleMediaType: Boolean): SelectionCreator {
        return Matisse.from(activity)
                .choose(mimeTypes)
                .countable(true)
                .capture(true)
                .showSingleMediaType(showSingleMediaType)
                .captureStrategy(CAPTURE_STRATEGY)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dracula)
                .imageEngine(Glide4Engine())
    }
}