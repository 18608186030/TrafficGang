package com.stjy.baselib.base.mvvm

open class BaseActionEvent(val action: Int) {

    var code: String? = null
    var message: String? = null

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
