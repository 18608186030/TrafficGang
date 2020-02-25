package com.stjy.baselib.wigiet.photopreview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author daifalin
 * @date 2018/10/23 6:06 PM
 * @ClassName PhotoPagerAdapter
 * @Description
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public PhotoPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
