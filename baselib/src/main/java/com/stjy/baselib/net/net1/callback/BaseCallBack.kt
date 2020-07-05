package com.stjy.baselib.net.net1.callback

import com.zhouyou.http.callback.CallBack

/**
 * @author daifalin
 * @date 2018/9/20 下午2:01
 * @ClassName CallBack
 * @Description
 */
abstract class BaseCallBack<T> : CallBack<T>() {
    override fun onStart() {}
    override fun onCompleted() {}
}