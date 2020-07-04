package com.stjy.baselib.base.cmmont

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: TabLayout的FragmentPagerAdapter
 */
class FragmentAdapter(fm: FragmentManager, private val mFragments: List<Fragment>, private val mTitles: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}

/**
 * @author daifalin
 * @date 2019/3/25 5:42 PM
 * @ClassName FragmentSubAdapter.java
 * @Description QMUITabSegment的FragmentPagerAdapter
 */
class FragmentSubAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}