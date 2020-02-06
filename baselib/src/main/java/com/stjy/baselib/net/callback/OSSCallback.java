package com.stjy.baselib.net.callback;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.stjy.baselib.utils.OSSUploadUtils;
import com.zhouyou.http.exception.ApiException;

/**
 * @author daifalin
 * @date 2018/12/28 5:30 PM
 * @ClassName OSSCallback
 * @Description
 */
public abstract class OSSCallback<T1 extends OSSRequest, T2 extends OSSResult> implements OSSCompletedCallback<T1, T2> {
    @Override
    public void onSuccess(T1 request, T2 result) {
        if (request instanceof PutObjectRequest && result instanceof PutObjectResult) {
            String objectKey = ((PutObjectRequest) request).getObjectKey();
            OSSUploadUtils.getInstance()
                    .getFileUrl(objectKey)
                    .execute(new BaseCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                            OSSCallback.this.onError(null, e.getMessage());
                        }

                        @Override
                        public void onSuccess(String fileUrl) {
                            if (TextUtils.isEmpty(fileUrl)) {
                                OSSCallback.this.onError(null, "获取图片链接失败");
                            } else {
                                OSSCallback.this.onSuccess(fileUrl);
                            }
                        }
                    });
        }
    }

    @Override
    public void onFailure(T1 request, ClientException clientException, ServiceException serviceException) {
        onError(clientException.getMessage(), serviceException.getMessage());
    }

    /**
     * 请求成功
     *
     * @param fileUrl 文件链接地址
     */
    public abstract void onSuccess(String fileUrl);

    /**
     * 请求异常
     */
    public abstract void onError(String clientErrorMsg, String serviceErrorMsg);
}
