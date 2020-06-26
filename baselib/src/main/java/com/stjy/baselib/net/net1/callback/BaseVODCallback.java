package com.stjy.baselib.net.net1.callback;


import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;

/**
 * @author daifalin
 * @date 2019/3/29 2:45 PM
 * @ClassName BaseVODCallback
 * @Description
 */
public abstract class BaseVODCallback {
    public void onUploadSucceed(UploadFileInfo info) {

    }

    public abstract void onUploadFinished(String videoUrl, String imageUrl);

    public abstract void onUploadFailed(UploadFileInfo info, String code, String message);

    public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {

    }

    public void onUploadStarted(UploadFileInfo uploadFileInfo) {

    }

}
