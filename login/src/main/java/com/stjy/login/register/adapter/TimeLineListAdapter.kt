package com.stjy.login.register.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stjy.baselib.base.MyAppliction.Companion.context
import com.stjy.login.R

/**
 * @author acb
 * @date 2018/6/29 10:26
 * @ClassName SearchEnterpriseListAdapter
 * @Description <这里用一句话描述这个类的作用>
</这里用一句话描述这个类的作用> */

class TimeLineListAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_itme_auditdetails, data) {
    @SuppressLint("NewApi")
    override fun convert(helper: BaseViewHolder, item: String) {
        //获取物流信息和物流时间的字体颜色, 最新的一条物流数据字体为绿色
        val newInfoColor = context.resources.getColor(if (helper.layoutPosition == 0) R.color.blue else R.color.gray)
        //当前item的索引==0 && 物流数据的数量大于1条   ->  显示绿色大圆圈
        helper.setGone(R.id.iv_new, helper.layoutPosition == 0 && data!!.size > 1)
                //当前item的索引!=0 && 物流数据的数量大于1条   ->  显示灰色小圆圈
                .setGone(R.id.iv_old, helper.layoutPosition != 0 && data!!.size > 1)
                //当前item的索引 != 0    ->  显示圆点上面短一点的灰线
                .setVisible(R.id.v_short_line, helper.layoutPosition != 0)
                //当前item的索引 != 物流数据的最后一条    ->  显示圆点下面长一点的灰线
                .setGone(R.id.v_long_line, helper.layoutPosition != data!!.size - 1)
                //当前item的索引 != 物流数据的最后一条    ->  显示物流时间下面的横向的灰线
                .setGone(R.id.v_bottom_line, helper.layoutPosition != data.size - 1)
                .setTextColor(R.id.tv_info, newInfoColor)
                .setTextColor(R.id.tv_date, newInfoColor)
                //物流信息
                .setText(R.id.tv_info, item)
                //物流时间
                .setText(R.id.tv_date, item)
    }
}