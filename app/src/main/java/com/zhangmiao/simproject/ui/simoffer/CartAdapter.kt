package com.zhangmiao.simproject.ui.simoffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.common.extension.showToast
import com.zhangmiao.simproject.logic.model.CartGoods

class CartAdapter(
    var cartGoods: ArrayList<CartGoods> = ArrayList(),
    private val callback: CartCallback
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val GOODS_LEAST_NUM = 1
    private val GOODS_MAX_NUM = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_goods, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartGoods: CartGoods = cartGoods.get(position)
        holder.cb_select.isChecked = cartGoods.select
        holder.cb_select.setOnClickListener {
            callback.changeCartGoodsSelect(cartGoods.id, holder.cb_select.isChecked)
        }
        holder.tv_goodsName.text = cartGoods.name
        holder.tv_price.text =
            SIMApplication.context.resources.getString(R.string.price, cartGoods.amount)

        holder.tv_reduce.setOnClickListener {
            if (cartGoods.num == GOODS_LEAST_NUM) {
                SIMApplication.context.getString(R.string.cart_quantity_least, GOODS_LEAST_NUM)
                    .showToast()
            } else {
                callback.reduceCartGoodsNum(cartGoods.id)
            }
        }
        holder.tv_num.text = cartGoods.num.toString()
        holder.tv_add.setOnClickListener {
            if (cartGoods.num == GOODS_MAX_NUM) {
                SIMApplication.context.getString(R.string.cart_quantity_max, GOODS_MAX_NUM)
                    .showToast()
            } else {
                callback.addCartGoodsNum(cartGoods.id)
            }
        }
    }

    override fun getItemCount() = cartGoods.size

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cb_select: CheckBox = view.findViewById(R.id.item_cart_goods_select_cb)
        val tv_goodsName: TextView = view.findViewById(R.id.item_cart_goods_name_tv)
        val tv_price: TextView = view.findViewById(R.id.item_cart_goods_price_tv)
        var tv_reduce: TextView = view.findViewById(R.id.item_cart_goods_reduce_tv)
        var tv_num: TextView = view.findViewById(R.id.item_cart_goods_num_tv)
        var tv_add: TextView = view.findViewById(R.id.item_cart_goods_add_tv)
    }

    interface CartCallback {
        fun changeCartGoodsSelect(id: String, select: Boolean)
        fun reduceCartGoodsNum(id: String)
        fun addCartGoodsNum(id: String)
    }
}