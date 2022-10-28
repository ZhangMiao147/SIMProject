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
import com.zhangmiao.simproject.logic.model.Goods
import com.zhangmiao.simproject.logic.model.Goods.CREATOR.REGULAR_PRICE_NO

class GoodsAdapter(
    var goodsList: List<Goods> = ArrayList<Goods>(),
    private val callback: GoodsCallback
) : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goods: Goods = goodsList[position]
        holder.tv_name.text = goods.name
        holder.tv_amountPrimary.text =
            SIMApplication.context.resources.getString(R.string.price, goods.amount_primary)

        if (goods.regular_price == REGULAR_PRICE_NO) {
            holder.tv_amountPrimary.setTextColor(Color.WHITE)
            holder.tv_regularPrice.visibility = View.INVISIBLE
        } else {

            holder.tv_amountPrimary.setTextColor(
                ContextCompat.getColor(
                    SIMApplication.context,
                    R.color.color_FFD71F
                )
            )
            holder.tv_regularPrice.visibility = View.VISIBLE
            holder.tv_regularPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.tv_regularPrice.text =
                SIMApplication.context.resources.getString(R.string.price, goods.regular_price)
        }
        holder.iv_addCart.setOnClickListener {
            callback.addCartGoods(goods)
        }
        holder.itemView.setOnClickListener {
            callback.gotoDetail(goods)
        }
    }

    override fun getItemCount() = goodsList.size


    inner class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name: TextView = view.findViewById(R.id.item_goods_name_tv)
        val tv_regularPrice: TextView = view.findViewById(R.id.item_goods_regular_price_tv)
        val tv_amountPrimary: TextView = view.findViewById(R.id.item_goods_amount_primary_tv)
        val iv_addCart: ImageView = view.findViewById(R.id.item_goods_add_cart_iv)
    }

    interface GoodsCallback {
        fun addCartGoods(good: Goods)
        fun gotoDetail(good: Goods)
    }

}