package com.zhangmiao.simproject.ui.simoffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.logic.model.ShoppingGood

class ShoppingAdapter(var shoppingGoods: ArrayList<ShoppingGood>?,val viewModel:SIMOfferViewModel) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    init {
        if (shoppingGoods == null){
            shoppingGoods = ArrayList<ShoppingGood>()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_good, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val shoppingGood: ShoppingGood = shoppingGoods!!.get(position)
        holder.cb_select.isChecked = shoppingGood.select
        holder.tv_shoppingName.text = shoppingGood.name
        holder.tv_price.text = shoppingGood.amount.toString()
        holder.tv_reduce.setOnClickListener {
            viewModel.reduceShoppingGoodNum(shoppingGood.id)
        }
        holder.tv_num.text = shoppingGood.num.toString()
        holder.tv_add.setOnClickListener {
            viewModel.addShoppingGoodNum(shoppingGood.id)
        }
    }

    override fun getItemCount() = shoppingGoods?.size?:0

    inner class ShoppingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cb_select: CheckBox = view.findViewById(R.id.item_shopping_good_select_cb)
        val tv_shoppingName: TextView = view.findViewById(R.id.item_shopping_good_name_tv)
        val tv_price: TextView = view.findViewById(R.id.item_shopping_good_price_tv)
        var tv_reduce: TextView = view.findViewById(R.id.item_shopping_good_reduce_tv)
        var tv_num: TextView = view.findViewById(R.id.item_shopping_good_num_tv)
        var tv_add: TextView = view.findViewById(R.id.item_shopping_good_add_tv)
    }
}