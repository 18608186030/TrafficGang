package com.stjy.baselib.utils;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.AppUtils;
import com.stjy.baselib.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.SelectionCreator;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.Set;

/**
 * @author daifalin
 * @date 2019/3/18 10:42 AM
 * @ClassName MatisseUtils
 * @Description
 */
public class MatisseUtils {
    public static final int CHOOSE_REQUEST = 222;
    public static final String[] PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String AUTHORITY = AppUtils.getAppPackageName() + ".matisseFileProvider";
    private static final CaptureStrategy CAPTURE_STRATEGY = new CaptureStrategy(true, AUTHORITY, "Camera");

    /**
     * 选择图片
     *
     * @param activity Activity instance.
     * @return
     */
    public static SelectionCreator fromImage(Activity activity) {
        return matisseFrom(activity, MimeType.ofImage(), true);
    }

    /**
     * 选择图片
     *
     * @param fragment Fragment instance.
     * @return
     */
    public static SelectionCreator fromImage(Fragment fragment) {
        return matisseFrom(fragment, MimeType.ofImage(), true);
    }

    /**
     * 选择视频
     *
     * @param activity Activity instance.
     * @return
     */
    public static SelectionCreator fromVideo(Activity activity) {
        return matisseFrom(activity, MimeType.ofVideo(), true).capture(false);
    }

    /**
     * 选择视频
     *
     * @param fragment Fragment instance.
     * @return
     */
    public static SelectionCreator fromVideo(Fragment fragment) {
        return matisseFrom(fragment, MimeType.ofVideo(), true).capture(false);
    }

    /**
     * 选择全部
     *
     * @param activity Activity instance.
     * @return
     */
    public static SelectionCreator fromAll(Activity activity) {
        return matisseFrom(activity, MimeType.ofAll(), false);
    }

    /**
     * 选择全部
     *
     * @param fragment Fragment instance.
     * @return
     */
    public static SelectionCreator fromAll(Fragment fragment) {
        return matisseFrom(fragment, MimeType.ofAll(), false);
    }

    private static SelectionCreator matisseFrom(Fragment fragment, Set<MimeType> mimeTypes, boolean showSingleMediaType) {
        return Matisse.from(fragment)
                .choose(mimeTypes)
                .countable(true)
                .capture(true)
                .showSingleMediaType(showSingleMediaType)
                .captureStrategy(CAPTURE_STRATEGY)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dracula)
                .imageEngine(new Glide4Engine());
    }

    private static SelectionCreator matisseFrom(Activity activity, Set<MimeType> mimeTypes, boolean showSingleMediaType) {
        return Matisse.from(activity)
                .choose(mimeTypes)
                .countable(true)
                .capture(true)
                .showSingleMediaType(showSingleMediaType)
                .captureStrategy(CAPTURE_STRATEGY)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dracula)
                .imageEngine(new Glide4Engine());
    }
}
