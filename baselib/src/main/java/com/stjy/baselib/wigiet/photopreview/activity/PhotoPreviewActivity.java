package com.stjy.baselib.wigiet.photopreview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.stjy.baselib.R;
import com.stjy.baselib.base.mvvm.BaseActivity;
import com.stjy.baselib.wigiet.photopreview.adapter.PhotoPagerAdapter;
import com.stjy.baselib.wigiet.photopreview.fragment.PhotoFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * @author acb
 */
public class PhotoPreviewActivity extends BaseActivity {

    public static final String POSITION = "BASICRES_POSITION";
    public static final String IMAGES = "BASICRES_IMAGE";
    private List<String> images;
    private int mPosition;
    private int size;
    private ViewPager vpPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initStatusBar() {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this,R.color.black));
        BarUtils.addMarginTopEqualStatusBarHeight(getToolbar());
        BarUtils.setStatusBarLightMode(this, false);
    }

    @Override
    public void initView() {
        getToolbar().setBackgroundResource(R.color.black);
        vpPhoto = findViewById(R.id.vpPhoto);
        Bundle bundle = getIntent().getExtras();
        mPosition = bundle.getInt(POSITION);
        images = bundle.getStringArrayList(IMAGES);
        if (images != null) {
            this.size = images.size();
            List<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                fragments.add(PhotoFragment.newInstance(images.get(i)));
            }
            PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), fragments);
            vpPhoto.setAdapter(photoPagerAdapter);
            vpPhoto.setCurrentItem(mPosition);
            setBarTitle(mPosition + 1 + "/" + size, ContextCompat.getColor(this, R.color.white));
        }
    }

    @Override
    public void initListener() {
        vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBarTitle(position + 1 + "/" + size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * @param context
     * @param position 当前图片位置
     * @param images   图片集合
     */
    public static void actionStart(Context context, int position, List<String> images) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putStringArrayList(IMAGES, (ArrayList<String>) images);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
