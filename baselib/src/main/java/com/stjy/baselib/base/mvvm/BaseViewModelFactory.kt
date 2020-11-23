package com.stjy.baselib.base.mvvm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: A creator is used to inject the product ID into the MvvmDemoViewModel
 *            This creator is to showcase how to inject dependencies into ViewModels.
 */
class BaseViewModelFactory constructor(val application: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val params = arrayOf<Class<*>>(Application::class.java)
            return modelClass.getConstructor(params[0]).newInstance(application) as T
        } catch (e: InstantiationException) {
            throw  RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw  RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }

    companion object {
        fun newInstance(application: Application) = BaseViewModelFactory(application)
    }
}
