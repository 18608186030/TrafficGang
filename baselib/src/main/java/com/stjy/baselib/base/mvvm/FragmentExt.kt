package com.stjy.baselib.base.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.stjy.baselib.base.app.BaseApplication

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, BaseMVVM_ViewModelFactory.newInstance(BaseApplication.application)).get(viewModelClass)

fun Fragment.getApplication() = BaseApplication.application