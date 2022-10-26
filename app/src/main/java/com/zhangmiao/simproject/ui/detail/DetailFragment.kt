package com.zhangmiao.simproject.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        Log.d(TAG,"onCreateView good:${good}")
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_detail, null, false);
        initView(view)
        return view
    }

    override fun initView(view: View) {
        super.initView(view)
        val tv_name: TextView = view.findViewById(R.id.activity_detail_name_tv)
        tv_name.text = good?.name ?: ""
        val tv_initPrice: TextView = view.findViewById(R.id.activity_detail_initial_amount_tv)

        if (good?.discount == 1) {
            // 有折扣
            tv_initPrice.text = "₱" + (good?.amount_initial.toString() ?: "--")
        } else {
            // 无折扣
            tv_initPrice.visibility = View.GONE
        }
        val tv_finalPrice: TextView = view.findViewById(R.id.activity_detail_final_amount_tv)
        tv_finalPrice.text = "₱" + (good?.amount_final.toString() ?: "--")
    }


}