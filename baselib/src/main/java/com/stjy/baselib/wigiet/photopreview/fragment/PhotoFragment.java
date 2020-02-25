package com.stjy.baselib.wigiet.photopreview.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.stjy.baselib.R;
import com.stjy.baselib.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author daifalin
 * @date 2018/10/23 6:07 PM
 * @ClassName PhotoFragment
 * @Description
 */
public class PhotoFragment extends BaseFragment {

    private PhotoView photoView;
    private String imageUrl;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    QMUITipDialog tipDialog;
    private final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_img_browse;
    }

    @Override
    protected void initView(View view) {
        photoView = view.findViewById(R.id.pvBrowse);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        imageUrl = arguments.getString("imageUrl");
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.photo_load_error_icon);
        Glide.with(getBaseActivity()).load(imageUrl).apply(options).into(photoView);
    }

    @Override
    protected void initListener() {
        photoView.setOnClickListener(v -> getBaseActivity().finish());

        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMenuDialog();
                return true;
            }
        });
    }

    private void showMenuDialog() {
        final String[] items = new String[]{"保存图片"};
        new QMUIDialog.MenuDialogBuilder(getActivity())
                .addItems(items, (dialog, which) -> {
                    requestPermission(() -> {
                        savePictureToLocal();
                    }, PERMISSIONS);
                    dialog.dismiss();
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void startLoadingDialog() {
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在保存")
                .create();
        tipDialog.setCancelable(false);
        tipDialog.show();
    }

    @Override
    public void stopLoadingDialog() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在保存")
                    .create();
            tipDialog.setCancelable(false);
        }
        tipDialog.dismiss();
    }

    public void savePictureToLocal() {
        Glide.with(getBaseActivity())
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        startLoadingDialog();
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (saveImageToGallery(mContext, resource)) {
                            ToastUtils.showShort("保存成功");
                        } else {
                            ToastUtils.showShort("保存失败");
                        }
                        stopLoadingDialog();
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        ToastUtils.showShort("保存失败");
                        stopLoadingDialog();
                    }
                });

    }

    //保存文件到指定路径
    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "NewEducation_images";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static PhotoFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString("imageUrl", imageUrl);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
