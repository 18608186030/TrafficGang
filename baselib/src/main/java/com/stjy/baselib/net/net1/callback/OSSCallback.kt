package com.stjy.baselib.net.net1.callback

import android.text.TextUtils
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.model.OSSRequest
import com.alibaba.sdk.android.oss.model.OSSResult
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.stjy.baselib.utils.OSSUploadUtils
import com.zhouyou.http.exception.ApiException

/**
 * @author daifalin
 * @date 2018/12/28 5:30 PM
 * @ClassName OSSCallback
 * @Description
 */
abstract class OSSCallback<T1 : OSSRequest?, T2 : OSSResult?> : OSSCompletedCallback<T1, T2> {
    override fun onSuccess(request: T1, result: T2) {
        if (request is PutObjectRequest && result is PutObjectResult) {
            val objectKey = (request as PutObjectRequest).objectKey
            OSSUploadUtils.getInstance()
                    .getFileUrl(objectKey)
                    .execute(object : BaseCallBack<String?>() {
                        override fun onError(e: ApiException) {
                            this@OSSCallback.onError(null, e.message)
                        }

                        override fun onSuccess(fileUrl: String?) {
                            if (TextUtils.isEmpty(fileUrl)) {
                                this@OSSCallback.onError(null, "获取图片链接失败")
                            } else {
                                this@OSSCallback.onSuccess(fileUrl)
                            }
                        }
                    })
        }
    }

    override fun onFailure(request: T1, clientException: ClientException, serviceException: ServiceException) {
        onError(clientException.message, serviceException.message)
    }

    /**
     * 请求成功
     *
     * @param fileUrl 文件链接地址
     */
    abstract fun onSuccess(fileUrl: String?)

    /**
     * 请求异常
     */
    abstract fun onError(clientErrorMsg: String?, serviceErrorMsg: String?)
}