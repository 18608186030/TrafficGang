package com.stjy.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.RawRes
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.Preconditions
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/05/16
 * desc  :
</pre> *
 */
object ImageLoaderUtils {
    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param view
     */
    fun loadCircleImage(context: Context, path: String?, view: ImageView) {
        loadCircleImage(context, path, 0, 0, view)
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param id
     * @param view
     */
    fun loadCircleImage(context: Context, @RawRes @DrawableRes id: Int?, view: ImageView) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop()
                .dontAnimate()
        Glide.with(context).load(id).apply(requestOptions).into(view)
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param view
     */
    fun loadCircleImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, view: ImageView) {
        loadCircleImage(context, path, placeholderResourceId, 0, view)
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param view
     */
    @SuppressLint("CheckResult")
    fun loadCircleImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, @DrawableRes errorResourceId: Int, view: ImageView) {
        val requestOptions = RequestOptions()
        if (placeholderResourceId != 0) {
            requestOptions.placeholder(placeholderResourceId)
        }
        if (errorResourceId != 0) {
            requestOptions.error(errorResourceId)
        }
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop()
                .dontAnimate()
        Glide.with(context).load(path).apply(requestOptions).into(view)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundImage(context: Context, path: String?, view: ImageView) {
        loadRoundImage(context, path, 0, 0, view, 5)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundImage(context: Context, path: String?, view: ImageView, roundingRadius: Int) {
        loadRoundImage(context, path, 0, 0, view, roundingRadius)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, view: ImageView, roundingRadius: Int) {
        loadRoundImage(context, path, placeholderResourceId, 0, view, roundingRadius)
    }

    /**
     * 加载圆角图片
     */
    @SuppressLint("CheckResult")
    fun loadRoundImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, @DrawableRes errorResourceId: Int, view: ImageView, roundingRadius: Int) {
        val roundedCorners = CenterCropRoundedCorners(SizeUtils.dp2px(roundingRadius.toFloat()))
        val requestOptions = RequestOptions()
                .transform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
        if (placeholderResourceId != 0) {
            requestOptions.placeholder(placeholderResourceId)
        }
        if (errorResourceId != 0) {
            requestOptions.error(errorResourceId)
        }
        Glide.with(context).load(path).apply(requestOptions).into(view)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path    图片地址
     * @param view
     */
    fun loadImage(context: Context, path: String?, view: ImageView) {
        loadImage(context, path, 0, 0, view)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path    图片地址
     * @param view
     */
    fun loadImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, view: ImageView?) {
        loadImage(context, path, placeholderResourceId, 0, view)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path    图片地址
     * @param view
     */
    @SuppressLint("CheckResult")
    fun loadImage(context: Context, path: String?, @DrawableRes placeholderResourceId: Int, @DrawableRes errorResourceId: Int, view: ImageView?) {
        val requestOptions = RequestOptions()
        if (placeholderResourceId != 0) {
            requestOptions.placeholder(placeholderResourceId)
        }
        if (errorResourceId != 0) {
            requestOptions.error(errorResourceId)
        }
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontAnimate()
        Glide.with(context).load(path).apply(requestOptions).into(view!!)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param file    图片文件
     * @param view
     */
    fun loadImage(context: Context, file: File?, view: ImageView?) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
        Glide.with(context).load(file).apply(requestOptions).into(view!!)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param uri     uri
     * @param view
     */
    fun loadImage(context: Context, uri: Uri?, view: ImageView?) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
        Glide.with(context).load(uri).apply(requestOptions).into(view!!)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param id      图片资源id
     * @param view
     */
    fun loadImage(context: Context, @RawRes @DrawableRes id: Int?, view: ImageView?) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
        Glide.with(context).load(id).apply(requestOptions).into(view!!)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url     图片链接
     * @param view
     */
    @SuppressLint("CheckResult")
    fun loadImageBlur(context: Context, url: String?, view: ImageView?, radius: Int) {
        loadImageBlur(context, url, 0, 0, view, radius)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url     图片链接
     * @param view
     */
    @SuppressLint("CheckResult")
    fun loadImageBlur(context: Context, url: String?, @DrawableRes placeholderResourceId: Int, view: ImageView?, radius: Int) {
        loadImageBlur(context, url, placeholderResourceId, 0, view, radius)
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url     图片链接
     * @param view
     */
    @SuppressLint("CheckResult")
    fun loadImageBlur(context: Context, url: String?, @DrawableRes placeholderResourceId: Int, @DrawableRes errorResourceId: Int, view: ImageView?, radius: Int) {
        val requestOptions = RequestOptions()
        if (placeholderResourceId != 0) {
            requestOptions.placeholder(placeholderResourceId)
        }
        if (errorResourceId != 0) {
            requestOptions.error(errorResourceId)
        }
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transform(BlurTransformation(radius))
                .dontAnimate()
        Glide.with(context).load(url).apply(requestOptions).into(view!!)
    }

    /**
     * 加载指定等边尺寸
     *
     * @param context
     * @param path    图片地址
     * @param size
     * @param view
     */
    fun loadImageSize(context: Context, path: String?, size: Int, view: ImageView?) {
        loadImageSize(context, path, size, size, view)
    }

    /**
     * 加载指定尺寸
     *
     * @param context
     * @param path
     * @param width
     * @param height
     * @param view
     */
    fun loadImageSize(context: Context, path: String?, width: Int, height: Int, view: ImageView?) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(width, height)
                .dontAnimate()
        Glide.with(context).load(path).apply(requestOptions).into(view!!)
    }

    /**
     * 加载gif
     *
     * @param context
     * @param path
     * @param view
     */
    fun loadImageGif(context: Context, path: String?, view: ImageView?) {
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
        Glide.with(context).asGif().load(path).apply(requestOptions).into(view!!)
    }

    /**
     * 加载gif
     *
     * @param context
     * @param resourceId
     * @param view
     */
    fun loadImageGif(context: Context, @RawRes @DrawableRes resourceId: Int?, view: ImageView?) {
        Glide.with(context).asGif().load(resourceId).into(view!!)
    }

    private fun loadSvgImage(context: Context, path: String, view: ImageView) {
        Glide.with(context)
                .`as`(PictureDrawable::class.java)
                .load(path)
                .into(view)
    }

    class CenterCropRoundedCorners(roundingRadius: Int) : CenterCrop() {
        private val roundingRadius: Int
        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            val bitmap = super.transform(pool, toTransform, outWidth, outHeight)
            return roundCrop(pool, bitmap)!!
        }

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) {
                return null
            }
            var result: Bitmap? = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(result)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, roundingRadius.toFloat(), roundingRadius.toFloat(), paint)
            return result
        }

        /**
         * @param roundingRadius the corner radius (in device-specific pixels).
         */
        init {
            Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.")
            this.roundingRadius = roundingRadius
        }
    }
}