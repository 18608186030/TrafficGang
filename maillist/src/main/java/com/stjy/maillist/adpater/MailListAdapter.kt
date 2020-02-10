package com.stjy.maillist.adpater

import android.annotation.SuppressLint

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.stjy.maillist.R
import com.stjy.maillist.modle.MailListResp

/**
 * @author acb
 * @date 2018/6/29 10:26
 * @ClassName MailListAdapter
 * @Description <这里用一句话描述这个类的作用>
</这里用一句话描述这个类的作用> */
class MailListAdapter internal constructor(data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.layout_itme_maillist_type1)
        addItemType(1, R.layout.layout_itme_maillist_type2)
        addItemType(2, R.layout.layout_itme_maillist_type3)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor", "NewApi")
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (helper.itemViewType) {
            0 -> {
                item as MailListResp.ListBeanXX
                helper.setText(R.id.title, item.title)
            }
            1 -> {
                item as MailListResp.ListBeanXX.ListBeanX
                helper.setText(R.id.title, item.title)
            }
            2 -> {
                item as MailListResp.ListBeanXX.ListBeanX.ListBean
                helper.setText(R.id.title, item.title)
            }
            else -> {
            }
        }
    }
}
