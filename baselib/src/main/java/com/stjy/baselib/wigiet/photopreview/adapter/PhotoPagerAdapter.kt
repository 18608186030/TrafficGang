package com.stjy.baselib.wigiet.photopreview.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


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