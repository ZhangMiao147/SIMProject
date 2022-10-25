package com.zhangmiao.simproject.ui.simoffer

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.logic.model.Good

/**
 * 商品列表 adapter
 */
class GoodsAdapter(
    var goodsList: List<Good> = ArrayList<Good>(),
    val viewModel: SIMOfferViewModel
) : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_good, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        var good: Good = goodsList[position]
        holder.tv_name.text = good.name
        holder.tv_priceFinal.text = good.amount_final.toString()
        if (good.discount == 1) {
            // 有折扣
            holder.tv_priceInitial.text = good.amount_initial.toString()
            holder.tv_priceInitial.visibility = View.VISIBLE
            holder.tv_priceInitial.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG and Paint.ANTI_ALIAS_FLAG
        } else {
            holder.tv_priceInitial.visibility = View.INVISIBLE
        }
        holder.iv_shopping.setOnClickListener {
            TODO("加入购物车")
            viewModel.addShoppingData(good)
        }
    }

    override fun getItemCount() = goodsList.size


    inner class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name: TextView = view.findViewById(R.id.item_good_name_tv)
        val tv_priceInitial: TextView = view.findViewById(R.id.item_good_price_initial_tv)
        val tv_priceFinal: TextView = view.findViewById(R.id.item_good_price_final_tv)
        val iv_shopping: ImageView = view.findViewById(R.id.item_good_shopping_iv)
    }
}