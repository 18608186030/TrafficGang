package com.stjy360.basicres

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 * This creator is to showcase how to inject dependencies into ViewModels.
 */
class ViewModelFactory constructor(
    val application: Application
) : ViewModelProvider.NewInstanceFactory() {

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
        fun newInstance(application: Application) = ViewModelFactory(application)
    }
}
