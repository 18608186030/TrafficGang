package com.stjy.maillist.modle

import android.os.Parcelable
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MailListResp(
        @SerializedName("list") var list: ArrayList<FirstListBean>? = null
) : Parcelable

@Parcelize
data class FirstListBean(
        @SerializedName("title") var title: String = "",
        @SerializedName("list") var list: ArrayList<SecondListBean>? = null
) : Parcelable, AbstractExpandableItem<SecondListBean>(), MultiItemEntity {
    override fun getLevel(): Int =0
    override fun getItemType(): Int = 0
}

@Parcelize
data class SecondListBean(
        @SerializedName("title") var title: String = ""
) : Parcelable, MultiItemEntity {
    override fun getItemType(): Int = 1
}