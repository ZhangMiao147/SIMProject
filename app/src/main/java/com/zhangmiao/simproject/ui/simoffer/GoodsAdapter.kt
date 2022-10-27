package com.zhangmiao.simproject.ui.simoffer

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.Good.CREATOR.REGULAR_PRICE_NO

class GoodsAdapter(
    var goodsList: List<Good> = ArrayList<Good>(),
    val callback: GoodCallback
) : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_good, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        var good: Good = goodsList[position]
        holder.tv_name.text = good.name
        holder.tv_priceFinal.text = SIMApplication.context.resources.getString(R.string.price,good.amount_primary)

        if (good.regular_price == REGULAR_PRICE_NO) {
            holder.tv_priceFinal.setTextColor(Color.WHITE)
            holder.tv_priceInitial.visibility = View.INVISIBLE
        } else {

            holder.tv_priceFinal.setTextColor(ContextCompat.getColor(SIMApplication.context,R.color.color_FFD71F))
            holder.tv_priceInitial.visibility = View.VISIBLE
            holder.tv_priceInitial.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.tv_priceInitial.text = SIMApplication.context.resources.getString(R.string.price,good.regular_price)
        }
        holder.iv_shopping.setOnClickListener {
            callback.addShopping(good)
        }
        holder.itemView.setOnClickListener {
            callback.gotoDetail(good)
        }
    }

    override fun getItemCount() = goodsList.size


    inner class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name: TextView = view.findViewById(R.id.item_good_name_tv)
        val tv_priceInitial: TextView = view.findViewById(R.id.item_good_price_initial_tv)
        val tv_priceFinal: TextView = view.findViewById(R.id.item_good_price_final_tv)
        val iv_shopping: ImageView = view.findViewById(R.id.item_good_shopping_iv)
    }

    open interface GoodCallback {
        fun addShopping(good: Good)
        fun gotoDetail(good: Good)
    }

}