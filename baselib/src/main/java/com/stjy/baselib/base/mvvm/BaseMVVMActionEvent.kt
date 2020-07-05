package com.stjy.baselib.base.mvvm

/**
 * @Author: superman
 * @CreateTime: 2020/7/4
 * @Describe: MVVM BaseMVVMActivity基类和BaseMVVMFragment基类通用事件
 */
open class BaseMVVMActionEvent(val action: Int?=null,
                               val code: String? = null,
                               val message: String? = null) {
    companion object {
        const val START_LOADING = 1
        const val STOP_LOADING = 2
        const val START_LOADING_DIALOG = 3
        const val STOP_LOADING_DIALOG = 4
        const val SHOW_TOAST = 5
        const val SHOW_ERROR = 6
        const val FINISH_REFRESH = 7
        const val FINISH_LOAD_MORE = 8
    }
}
