package com.stjy.baselib.base.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact { replace(frameId, fragment) }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact { add(fragment, tag) }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply { action() }.commit()
}

fun <T : ViewModel> AppCompatActivity.obtainViewModelWithActivity(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, BaseViewModelFactory.newInstance(application)).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModelWithFragment(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, this.activity?.application?.let { BaseViewModelFactory.newInstance(it) }).get(viewModelClass)
