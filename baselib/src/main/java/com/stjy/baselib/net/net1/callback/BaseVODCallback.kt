package com.stjy.baselib.net.net1.callback

import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo

/**
 * @author daifalin
 * @date 2019/3/29 2:45 PM
 * @ClassName BaseVODCallback
 * @Description
 */
abstract class BaseVODCallback {
    fun onUploadSucceed(info: UploadFileInfo?) {}
    abstract fun onUploadFinished(videoUrl: String?, imageUrl: String?)
    abstract fun onUploadFailed(info: UploadFileInfo?, code: String?, message: String?)
    fun onUploadProgress(info: UploadFileInfo?, uploadedSize: Long, totalSize: Long) {}
    fun onUploadStarted(uploadFileInfo: UploadFileInfo?) {}
}