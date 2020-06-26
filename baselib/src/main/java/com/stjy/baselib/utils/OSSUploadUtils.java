package com.stjy.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.ThreadUtils;
import com.stjy.baselib.common.LoginUser;
import com.stjy.baselib.model.UserBean;
import com.stjy.baselib.net.AppConfig;
import com.stjy.baselib.net.net1.HttpManager;
import com.stjy.baselib.net.net1.callback.OSSCallback;
import com.stjy.baselib.net.net1.request.BaseGetRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

/**
 * @author daifalin
 * @date 2018/12/28 2:44 PM
 * @ClassName OssUploadUtils
 * @Description
 */
public class OSSUploadUtils {
    public static String OSS_BUCKET_NAME = "anquanyunketang";
    private static OSSUploadUtils sOSSUploadUtils;
    private static OSSClient sOSSClient;
    private static Context mContext;

    private OSSUploadUtils() {

    }

    public static void init(Context context) {
        mContext = context;
        getInstance();
    }

    public static OSSUploadUtils getInstance() {
        if (sOSSUploadUtils == null) {
            synchronized (OSSUploadUtils.class) {
                if (sOSSUploadUtils == null) {
                    sOSSUploadUtils = new OSSUploadUtils();
                    initOSSClient();
                }
            }
        }
        return sOSSUploadUtils;
    }

    /**
     * 获取OSSClient
     *
     * @return OSSClient
     */
    public OSSClient getOSSClient() {
        return sOSSClient;
    }

    /**
     * 初始化OSSClient
     *
     * @return
     */
    private static void initOSSClient() {
        OSSAuthCredentialsProvider credentialProvider = new OSSAuthCredentialsProvider(AppConfig.STS_SERVER_URL);
        ClientConfiguration conf = new ClientConfiguration();
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(0);
        if (AppConfig.isDebug) {
            OSSLog.enableLog();
        }
        ThreadUtils.getSinglePool()
                .submit(() -> {
                    sOSSClient = new OSSClient(mContext, AppConfig.OSS_ENDPOINT, credentialProvider);
                });
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param localFile 文件路径
     * @param callback  回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull String objectKey,
                                      @NonNull String localFile,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback) {
        File file = new File(localFile);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey) + "." + suffix, localFile);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }


    /**
     * 异步上传图片
     *
     * @param bitmap   图片
     * @param callback 回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull Bitmap bitmap,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imgBytes = baos.toByteArray();
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(UUID.randomUUID().toString()) + System.currentTimeMillis() + ".jpg", imgBytes);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param localFile 文件路径
     * @param callback  回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull String objectKey,
                                      @NonNull File localFile,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback) {
        String fileName = localFile.getName();
        String filePath = localFile.getPath();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey) + "." + suffix, filePath);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param localFile 文件路径
     */
    public PutObjectResult putImage(@NonNull String objectKey, @NonNull String localFile) throws ClientException, ServiceException {
        File file = new File(localFile);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey) + "." + suffix, localFile);
        return sOSSClient.putObject(put);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param localFile 文件路径
     */
    public PutObjectResult putImage(@NonNull String objectKey, @NonNull File localFile) throws ClientException, ServiceException {
        String fileName = localFile.getName();
        String filePath = localFile.getPath();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey) + "." + suffix, filePath);
        return sOSSClient.putObject(put);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param localFile 文件路径
     * @param callback  回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull String objectKey,
                                      @NonNull String localFile,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback,
                                      @NonNull ObjectMetadata metadata) {
        File file = new File(localFile);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey) + "." + suffix, localFile, metadata);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param uploadData 数组
     * @param callback   回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull String objectKey,
                                      @NonNull byte[] uploadData,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback) {
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey), uploadData);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }

    /**
     * 异步上传图片
     *
     * @param objectKey
     * @param uploadData 数组
     * @param callback   回调
     */
    public OSSAsyncTask asyncPutImage(@NonNull String objectKey,
                                      @NonNull byte[] uploadData,
                                      OSSProgressCallback<PutObjectRequest> progressCallback,
                                      @NonNull OSSCallback<PutObjectRequest, PutObjectResult> callback,
                                      @NonNull ObjectMetadata metadata) {
        // 构造上传请求
        PutObjectRequest put = getPutObjectRequest(getObjectKeyPath(objectKey), uploadData, metadata);
        if (progressCallback != null) {
            // 异步上传时可以设置进度回调
            put.setProgressCallback(progressCallback);
        }
        return sOSSClient.asyncPutObject(put, callback);
    }

    /**
     * 生成图片Url
     *
     * @param objectKey 上传时填写的objectKey
     * @return
     */
    public String presignConstrainedObjectURL(String objectKey) {
        try {
            return OSSUploadUtils.getInstance()
                    .getOSSClient()
                    .presignConstrainedObjectURL(OSS_BUCKET_NAME, objectKey,
                            365 * 50 * 24 * 60 * 60);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成路径
     *
     * @param objectKey
     * @return
     */
    private String getObjectKeyPath(String objectKey) {
        UserBean user = LoginUser.getInstance().getUser();
        String publicPath = "android/";
        if (user != null) {
            String idCard = user.getIdcard();
            String id = user.getId();
            if (idCard != null) {
                return publicPath + idCard + "/" + objectKey;
            } else if (id != null) {
                return publicPath + id + "/" + objectKey;
            } else {
                return publicPath + objectKey;
            }
        }
        return publicPath + objectKey;
    }

    public BaseGetRequest getFileUrl(String objectKey) {
        return HttpManager.get(AppConfig.OSS_FILE_URL)
                .params("objectKey", objectKey);
    }

    private PutObjectRequest getPutObjectRequest(@NonNull String objectKey,
                                                 @NonNull String localFile) {
        return new PutObjectRequest(OSS_BUCKET_NAME, objectKey, localFile);
    }

    private PutObjectRequest getPutObjectRequest(@NonNull String objectKey,
                                                 @NonNull String localFile,
                                                 @NonNull ObjectMetadata metadata) {
        return new PutObjectRequest(OSS_BUCKET_NAME, objectKey, localFile, metadata);
    }

    private PutObjectRequest getPutObjectRequest(@NonNull String objectKey,
                                                 @NonNull byte[] uploadData) {
        return new PutObjectRequest(OSS_BUCKET_NAME, objectKey, uploadData);
    }

    private PutObjectRequest getPutObjectRequest(@NonNull String objectKey,
                                                 @NonNull byte[] uploadData,
                                                 @NonNull ObjectMetadata metadata) {
        return new PutObjectRequest(OSS_BUCKET_NAME, objectKey, uploadData, metadata);
    }
}
