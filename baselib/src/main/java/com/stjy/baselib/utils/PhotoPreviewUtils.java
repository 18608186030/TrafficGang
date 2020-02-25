package com.stjy.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stjy.baselib.wigiet.photopreview.activity.PhotoPreviewActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 代发琳 on 17-9-20.
 * 图片预览工具类
 * function：
 */

public class PhotoPreviewUtils {
    public static final String POSITION = "BASICRES_POSITION";
    public static final String IMAGES = "BASICRES_IMAGE";

    /**
     * 跳转图片预览
     */
    public static void start(Context context, int position, List<String> images) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putStringArrayList(IMAGES, (ArrayList<String>) images);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 跳转图片预览
     */
    public static void start(Context context, int position, String image) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putStringArrayList(IMAGES, new ArrayList<String>() {{
            add(image);
        }});
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
