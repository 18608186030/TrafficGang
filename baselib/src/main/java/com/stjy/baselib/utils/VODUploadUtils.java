package com.stjy.baselib.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.sdk.android.vod.upload.ResumableVODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.alibaba.sdk.android.vod.upload.model.VodUploadResult;
import com.blankj.utilcode.util.LogUtils;
import com.stjy.baselib.model.VODSTSBean;
import com.stjy.baselib.net.HttpConstant;
import com.stjy.baselib.net.HttpManager;
import com.stjy.baselib.net.callback.BaseCallBack;
import com.stjy.baselib.net.callback.BaseVODCallback;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author daifalin
 * @date 2019/3/29 2:34 PM
 * @ClassName VODUploadUtils
 * @Description
 */
public class VODUploadUtils {

    public static final String REGION = "cn-beijing";
    private static VODUploadUtils sVODUploadUtils;
    private static Context mContext;
    private String mExpiration;
    private String mSecurityToken;
    private String mAccessKeySecret;
    private String mAccessKeyId;

    private VODUploadUtils() {
        getSTS();
    }

    public static void init(Context context) {
        mContext = context;
        getInstance();
    }

    public static VODUploadUtils getInstance() {
        if (sVODUploadUtils == null) {
            synchronized (VODUploadUtils.class) {
                if (sVODUploadUtils == null) {
                    sVODUploadUtils = new VODUploadUtils();
                }
            }
        }
        return sVODUploadUtils;
    }

    /**
     * 获取STS鉴权信息
     */
    private void getSTS() {
        HttpManager.post(HttpConstant.STS_VOD_SERVER_URL)
                .execute(new BaseCallBack<VODSTSBean>() {

                    @Override
                    public void onError(ApiException e) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onSuccess(VODSTSBean vodstsBean) {
                        mAccessKeyId = vodstsBean.getAccessKeyId();
                        mAccessKeySecret = vodstsBean.getAccessKeySecret();
                        mSecurityToken = vodstsBean.getSecurityToken();
                        mExpiration = vodstsBean.getExpiration();
                    }
                });
    }

    /**
     * 获取 VODUploadClientImpl
     *
     * @param vodCallback 上传回调
     * @return VODUploadClientImpl
     */
    public VODUploadClient getVODUploadClient(@NonNull BaseVODCallback vodCallback) {
        VODUploadClient vodUploadClient = new VODUploadClientImpl(mContext);
        //vodUploadClient.setRegion(REGION);
        vodUploadClient.setPartSize(1024 * 1024);
        vodUploadClient.setRecordUploadProgressEnabled(true);
        ResumableVODUploadCallback vodUploadCallback = new ResumableVODUploadCallback() {

            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                LogUtils.d("onUploadSucceed");
                vodCallback.onUploadSucceed(info);
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                LogUtils.d("onUploadFailed" + code + message);
                vodCallback.onUploadFailed(info, code, message);
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
                LogUtils.d("onUploadProgress:" + uploadedSize + "---" + totalSize);
                vodCallback.onUploadProgress(info, uploadedSize, totalSize);
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                LogUtils.d("onUploadStarted");
                vodCallback.onUploadStarted(uploadFileInfo);
            }

            @Override
            public void onUploadFinished(UploadFileInfo uploadFileInfo, VodUploadResult result) {
                LogUtils.d("onUploadFinished");
                String videoid = result.getVideoid();
                String imageUrl = result.getImageUrl();
                vodCallback.onUploadFinished("http://api.jsy360.cn/get.php?VideoId=" + videoid, imageUrl);
            }

            @Override
            public void onUploadRetry(String code, String message) {
                LogUtils.d("onUploadRetry");

            }

            @Override
            public void onUploadRetryResume() {
                LogUtils.d("onUploadRetryResume");
            }


            @Override
            public void onUploadTokenExpired() {
                LogUtils.d("onUploadTokenExpired");
                HttpManager.post(HttpConstant.STS_VOD_SERVER_URL)
                        .execute(new BaseCallBack<VODSTSBean>() {

                            @Override
                            public void onError(ApiException e) {

                            }

                            @Override
                            public void onSuccess(VODSTSBean vodstsBean) {
                                vodUploadClient.resumeWithToken(vodstsBean.getAccessKeyId(),
                                        vodstsBean.getAccessKeySecret(),
                                        vodstsBean.getSecurityToken(),
                                        vodstsBean.getExpiration());
                            }
                        });
            }
        };
        vodUploadClient.init(mAccessKeyId, mAccessKeySecret, mSecurityToken, mExpiration, vodUploadCallback);
        return vodUploadClient;
    }

    /**
     * 上传视频文件
     *
     * @param filePath    视频文件路径
     * @param vodInfo     视频文件信息
     *                    VodInfo vodInfo = new VodInfo();
     *                    vodInfo.setTitle("标题" + index);
     *                    vodInfo.setDesc("描述." + index);
     *                    vodInfo.cateId (19);
     *                    vodInfo.tags("sports");
     * @param vodCallback 视频文件回调
     * @return VODUploadClientImpl
     */
    public VODUploadClient uploadVODFile(@NonNull String filePath, @NonNull VodInfo vodInfo, @NonNull BaseVODCallback vodCallback) {
        VODUploadClient vodUploadClient = getVODUploadClient(vodCallback);
        List<String> tag = new ArrayList<>();
        tag.add("车友圈");
        vodInfo.setTags(tag);
        vodInfo.setTitle("车友圈");
        vodUploadClient.addFile(filePath, vodInfo);
        return vodUploadClient;
    }

    /**
     * 上传视频文件
     *
     * @param map         视频文件
     *                    String filePath
     *                    <p>
     *                    VodInfo vodInfo = new VodInfo();
     *                    vodInfo.setTitle("标题" + index);
     *                    vodInfo.setDesc("描述." + index);
     *                    vodInfo.cateId (19);
     *                    vodInfo.tags("sports");
     * @param vodCallback 视频文件回调
     * @return VODUploadClientImpl
     */
    public VODUploadClient uploadVODFile(@NonNull Map<String, VodInfo> map, @NonNull BaseVODCallback vodCallback) {
        VODUploadClient vodUploadClient = getVODUploadClient(vodCallback);
        for (String key : map.keySet()) {
            VodInfo vodInfo = map.get(key);
            List<String> tag = new ArrayList<>();
            tag.add("车友圈");
            vodInfo.setTags(tag);
            vodInfo.setTitle("车友圈");
            vodUploadClient.addFile(key, vodInfo);
        }
        return vodUploadClient;
    }
}
