package com.zhangmiao.simproject.ui.detail

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.common.ui.BaseActivity
import com.zhangmiao.simproject.logic.model.Good

/**
 * 商品详情页
 */
class DetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_detail)
        val good: Good? = intent.getParcelableExtra<Good>("good")
        initView(good)
        setAppBar("",true)
    }

    private fun initView(good: Good?) {
        val tv_name: TextView = findViewById(R.id.activity_detail_name_tv)
        tv_name.text = good?.name?:""
        val tv_initPrice:TextView = findViewById(R.id.activity_detail_initial_amount_tv)

        if (good?.discount == 1){
            // 有折扣
            tv_initPrice.text = good?.amount_initial.toString()?:"--"
        } else {
            // 无折扣
            tv_initPrice.visibility = View.GONE
        }
        val tv_finalPrice:TextView = findViewById(R.id.activity_detail_final_amount_tv)
        tv_finalPrice.text = good?.amount_final.toString()?:"--"
    }
}