package com.stjy.baselib.bean.model


/**
 * @Author: superman
 * @CreateTime: 2020/7/5
 * @Describe:
 */
class ZIXunAllListResp {
    var article_list_count = 0
    var other_list: List<OtherListBean>? = null

    class OtherListBean {
        var id = 0
        var title = ""
        var image = ""
    }
}