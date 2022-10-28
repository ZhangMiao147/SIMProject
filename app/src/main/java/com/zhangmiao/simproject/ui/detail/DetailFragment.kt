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
import com.zhangmiao.simproject.logic.model.Goods

class DetailFragment : BaseFragment() {

    private var goods: Goods? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        goods = arguments?.getParcelable<Goods>(ARGUMENTS_GOODS)
        Log.d(TAG, "onCreateView goods:${goods}")
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_detail, null, false);
        initView(view)
        return view
    }

    override fun initView(view: View) {
        super.initView(view)
        val tv_name: TextView = view.findViewById(R.id.fragment_detail_name_tv)
        tv_name.text = goods?.name ?: ""

        val tv_amountPrimary: TextView = view.findViewById(R.id.fragment_detail_amount_primary_tv)
        tv_amountPrimary.text = resources.getString(R.string.price,(goods?.amount_primary ?: 0))

        val tv_regularPrice: TextView = view.findViewById(R.id.fragment_detail_regular_price_tv)
        if (goods?.regular_price != Goods.REGULAR_PRICE_NO) {
            tv_regularPrice.text = resources.getString(R.string.price,(goods?.regular_price ?: 0))
            tv_regularPrice.visibility = View.VISIBLE
            tv_regularPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            tv_amountPrimary.setTextColor(resources.getColor(R.color.color_FFD71F))
        } else {
            tv_regularPrice.visibility = View.GONE
            tv_amountPrimary.setTextColor(Color.WHITE)
        }

        val group_overview: Group = view.findViewById(R.id.fragment_detail_overview_group)
        if (goods?.description.isNullOrEmpty()) {
            group_overview.visibility = View.GONE
        } else {
            group_overview.visibility = View.VISIBLE
            val tv_overviewContent: TextView =
                view.findViewById(R.id.fragment_detail_overview_content_tv)
            tv_overviewContent.text = goods?.description
        }

        val group_detail: Group = view.findViewById(R.id.fragment_detail_product_detail_group)
        if (goods?.detail.isNullOrEmpty()) {
            group_detail.visibility = View.GONE
        } else {
            group_detail.visibility = View.VISIBLE
            val tv_productDetailContent: TextView =
                view.findViewById(R.id.fragment_detail_delivery_detail_title_tv)
            tv_productDetailContent.text = goods?.detail
        }

    }

    companion object{
        const val ARGUMENTS_GOODS = "goods"
    }


}