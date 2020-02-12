package com.stjy.login.register.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stjy.login.R

/**
 * @author acb
 * @date 2018/6/29 10:26
 * @ClassName SearchEnterpriseListAdapter
 * @Description <这里用一句话描述这个类的作用>
</这里用一句话描述这个类的作用> */

class SearchEnterpriseListAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.layout_itme_searchenterpriselist, data
) {
    @SuppressLint("NewApi")
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.title, item)
    }
}