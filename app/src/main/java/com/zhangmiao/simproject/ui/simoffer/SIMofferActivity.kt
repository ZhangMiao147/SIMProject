package com.zhangmiao.simproject.ui.simoffer

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.common.ui.BaseActivity
import com.zhangmiao.simproject.logic.model.OffersRequest

/**
 * 商品列表界面
 */
class SIMofferActivity : BaseActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(SIMOfferViewModel::class.java)
    }

    private lateinit var adapter: GoodsAdapter
    private lateinit var rv_list: RecyclerView
    private lateinit var tv_selectNum: TextView
    private lateinit var tv_totalAmount: TextView
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim_offer)
        initView()
        setAppBar("SIM offers", false)
        addObserve()
        showLoading()
        viewModel.getGoods(OffersRequest("offers", "globegomo"))
    }

    override fun initView() {
        super.initView()
        rv_list = findViewById(R.id.activity_sim_offer_list_rv)
        val layoutManager = GridLayoutManager(this, 2)
        rv_list.layoutManager = layoutManager
        adapter = GoodsAdapter(viewModel.goodList, viewModel)
        rv_list.adapter = adapter

        val shoppingListGroup:Group = findViewById(R.id.activity_sim_offer_shopping_list_group)

        val iv_shopping: ImageView = findViewById(R.id.activity_sim_offer_shopping_iv)
        iv_shopping.setOnClickListener {
            if (shoppingListGroup.visibility != View.VISIBLE){
                shoppingListGroup.visibility = View.VISIBLE
            }
        }

        tv_selectNum = findViewById(R.id.activity_sim_offer_shopping_select_num_tv)

        tv_totalAmount = findViewById(R.id.activity_sim_offer_shopping_total_amount_tv)

        val tv_checkout: TextView = findViewById(R.id.activity_sim_offer_shopping_checkout_tv)
        tv_checkout.setOnClickListener {
            Toast.makeText(SIMApplication.context,"结算商品",Toast.LENGTH_SHORT).show()
        }

        val rv_shoppingList: RecyclerView = findViewById(R.id.activity_sim_offer_shopping_list_rv)
        rv_shoppingList.layoutManager = LinearLayoutManager(this)
        shoppingAdapter = ShoppingAdapter(viewModel.shoppingList.value, viewModel)
        rv_shoppingList.adapter = shoppingAdapter

        val tv_clear:TextView = findViewById(R.id.activity_sim_offer_shopping_clear_tv)
        tv_clear.setOnClickListener {
            viewModel.clearShoppingGoods()
        }
    }

    private fun addObserve() {
        viewModel.goodLiveData.observe(this, androidx.lifecycle.Observer { result ->
            Log.d(TAG,"addObserve goodLiveData observe result:${result}")
            val goods = result.getOrNull()
            Log.d(TAG,"addObserve goodLiveData observe goods:${goods}")
            if (!goods.isNullOrEmpty()) {
                hideLoading()
                viewModel.goodList.clear()
                viewModel.goodList.addAll(goods)
                adapter.goodsList = goods
                adapter.notifyDataSetChanged()
            } else {
                hideLoading()
                showEmptyView()
            }
        })

        viewModel.shoppingList.observe(this, Observer {
            tv_selectNum.text = viewModel.getSelectNum().toString()
            tv_totalAmount.text = "₱" + viewModel.getTotalAmount()
            shoppingAdapter.shoppingGoods = viewModel.shoppingList.value
        })
    }

}