package com.zhangmiao.simproject.ui.detail

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.common.ui.BaseFragment
import com.zhangmiao.simproject.logic.model.Good

class DetailFragment : BaseFragment() {

    private var good: Good? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        good = arguments?.getParcelable<Good>("good")
        Log.d(TAG, "onCreateView good:${good}")
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_detail, null, false);
        initView(view)
        return view
    }

    override fun initView(view: View) {
        super.initView(view)
        val tv_name: TextView = view.findViewById(R.id.activity_detail_name_tv)
        tv_name.text = good?.name ?: ""

        val tv_finalPrice: TextView = view.findViewById(R.id.activity_detail_final_amount_tv)
        tv_finalPrice.text = "₱" + (good?.amount_primary.toString() ?: "--")

        val tv_initPrice: TextView = view.findViewById(R.id.activity_detail_initial_amount_tv)
        if (good?.regular_price != -1) {
            tv_initPrice.text = "₱" + (good?.regular_price.toString() ?: "--")
            tv_initPrice.visibility = View.VISIBLE
            tv_initPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            tv_finalPrice.setTextColor(Color.parseColor("#FFD71F"))
        } else {
            tv_initPrice.visibility = View.GONE
            tv_finalPrice.setTextColor(Color.WHITE)
        }

        val group_overview: Group = view.findViewById(R.id.activity_detail_overview_group)
        if (good?.description.isNullOrEmpty()) {
            group_overview.visibility = View.GONE
        } else {
            group_overview.visibility = View.VISIBLE
            val tv_overviewContent: TextView =
                view.findViewById(R.id.activity_detail_overview_content_tv)
            tv_overviewContent.text = good?.description
        }

        val group_detail: Group = view.findViewById(R.id.activity_detail_product_detail_group)
        if (good?.detail.isNullOrEmpty()) {
            group_detail.visibility = View.GONE
        } else {
            group_detail.visibility = View.VISIBLE
            val tv_productDetailContent: TextView =
                view.findViewById(R.id.activity_detail_product_detail_content_tv)
            tv_productDetailContent.text = good?.detail
        }


    }


}