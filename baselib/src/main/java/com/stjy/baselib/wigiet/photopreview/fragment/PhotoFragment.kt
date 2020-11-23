package com.stjy.baselib.wigiet.photopreview.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.PhotoView
import com.kongzue.dialog.v3.BottomMenu
import com.stjy.baselib.R
import com.stjy.baselib.base.mvc.BaseFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * @author daifalin
 * @date 2018/10/23 6:07 PM
 * @ClassName PhotoFragment
 * @Description
 */
class PhotoFragment : BaseFragment() {
    private var photoView: PhotoView? = null
    private var imageUrl: String? = null
    private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    override fun getLayoutID(): Int {
        return R.layout.fragment_img_browse
    }

    override fun initView(view: View?) {
        photoView = view?.findViewById(R.id.pvBrowse)
    }

    override fun initData() {
        val arguments = arguments
        imageUrl = arguments?.getString("imageUrl")
        val options = RequestOptions()
        options.placeholder(R.drawable.photo_load_error_icon)
        photoView?.let { Glide.with(mActivity).load(imageUrl).apply(options).into(it) }
    }

    override fun initListener() {
        photoView?.setOnClickListener { v: View? -> mActivity.finish() }
        photoView?.setOnLongClickListener {
            showMenuDialog()
            true
        }
    }

    private fun showMenuDialog() {

        BottomMenu.show(mActivity, arrayOf("保存图片")) { text, index ->
            //注意此处的 text 返回为自定义 Adapter.getItem(position).toString()，如需获取自定义Object，请尝试 datas.get(index)
            when (text) {
                "保存图片" -> requestPermission(*PERMISSIONS, success = {
                    savePictureToLocal()
                })
            }
        }.title = "请选择..."
    }

    fun savePictureToLocal() {
        Glide.with(mActivity)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap?>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        startLoadingDialog("保存中...")
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        ToastUtils.showShort("保存失败")
                        stopLoadingDialog()
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        if (saveImageToGallery(_mActivity, resource)) {
                            ToastUtils.showShort("保存成功")
                        } else {
                            ToastUtils.showShort("保存失败")
                        }
                        stopLoadingDialog()
                    }
                })
    }

    //保存文件到指定路径
    fun saveImageToGallery(context: Context, bmp: Bitmap): Boolean {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "NewEducation_images")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val file = File(appDir, System.currentTimeMillis().toString() + ".jpg")
        try {
            var isSuccess: Boolean
            with(FileOutputStream(file)) {
                //通过io流的方式来压缩保存图片
                isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, this)
                this.flush()
                this.close()
                this
            }

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            return isSuccess
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(imageUrl: String?): PhotoFragment {
            val args = Bundle()
            args.putString("imageUrl", imageUrl)
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}