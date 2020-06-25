package com.stjy.baselib.wigiet.photopreview.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * @author daifalin
 * @date 2018/10/23 6:06 PM
 * @ClassName PhotoPagerAdapter
 * @Description
 */
class PhotoPagerAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment =mFragments[i]

    override fun getCount(): Int =mFragments.size
}