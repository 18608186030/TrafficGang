package com.stjy.work.bean

import com.google.gson.annotations.SerializedName

class ZiXunAllResp {
    var article_list_count = 0
    var other_list: List<OtherListBean>? = null

    class OtherListBean {
        var id = 0
        var title: String? = null

        @SerializedName("abstract")
        var abstractX: String? = null
        var product = 0
        var price = 0
        var url: String? = null
        var like_num = 0
        var comment_num = 0
        var tag_id = 0
        var isCharge = 0
        var author_id = 0
        var author_name: String? = null
        var remark: String? = null
        var image: String? = null
        var status = 0
        var release_type = 0
        var timer_date: String? = null
        var audit_user_id = 0
        var audit_user_name: String? = null
        var is_delete = 0
        var is_hot = 0
        var is_top = 0
        var is_must = 0
        var is_topic = 0
        var create_datetime: String? = null
        var audit_datetime: String? = null
        var update_datetime: String? = null
        var content_video: String? = null
        var content_video_time: String? = null
        var content_audio: String? = null
        var content_stock: String? = null
        var content_audio_time: String? = null
        var tag: String? = null
        var teacher: TeacherBean? = null
        var article_product: ArticleProductBean? = null
        var is_praise = 0
        var pay_or_not = 0
        var is_follow = 0

        class TeacherBean {
            var user_label: String? = null
            var is_delete = 0
            var teacher_logo: String? = null
            var teacher_ask_product = 0
            var private_introductory: String? = null
            var create_datetime: String? = null
            var teacher_level: String? = null
            var status = 0
            var private_recommend_img: String? = null
            var modify_datetime: String? = null
            var teacher_tage: String? = null
            var is_top = 0
            var big_v: String? = null
            var last_login_datetime: String? = null
            var email: String? = null
            var private_recommend = 0
            var invite_code: String? = null
            var id = 0
            var mobile: String? = null
            var sort = 0
            var teacher_name: String? = null
            var tg_tabs: String? = null
            var teacher_info: String? = null
            var is_recommended = 0
            var account: String? = null
            var teacher_fans_num = 0
            var teacher_num: String? = null
            var role_id = 0
            var teacher_ask_price = 0
            var teacher_url: String? = null
            var private_sort = 0

        }

        class ArticleProductBean {
            var id = 0
            var apple_code: String? = null
            var kind: String? = null
            var subject: String? = null
            var price_unit = 0
            var body: String? = null
            var bigicon: String? = null
            var is_delete = 0
            var smallicon: String? = null
            var creator = 0
            var extime: String? = null
            var create_datetime: String? = null
            var tape_price: String? = null
            var modifier = 0
            var real_price: String? = null
            var modify_datetime: String? = null
            var price_type: String? = null
            var gold = 0
        }
    }
}