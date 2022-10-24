package com.zhangmiao.simproject.ui.simoffer

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
class GoodsAdapter(private val goodsList:List<Good> = ArrayList<Good>(),val viewModel: SIMOfferViewModel):RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_good,parent,false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        var good:Good = goodsList[position]
        holder.tv_name.text = good.name
        holder.tv_price.text = good.amount_final.toString()
        holder.iv_shopping.setOnClickListener {
            TODO("加入购物车")
            viewModel.addShoppingData(good)
        }
    }

    override fun getItemCount() = goodsList.size



    inner class GoodsViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tv_name:TextView = view.findViewById(R.id.item_good_name_tv)
        val tv_price:TextView = view.findViewById(R.id.item_good_price_final_tv)
        val iv_shopping:ImageView = view.findViewById(R.id.item_good_shopping_iv)
    }
}