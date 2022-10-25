package com.zhangmiao.simproject.ui.simoffer

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.common.ui.BaseFragment
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.OffersRequest

class SIMOfferFragment : BaseFragment() {

    private lateinit var adapter: GoodsAdapter
    private lateinit var rv_list: RecyclerView
    private lateinit var tv_selectNum: TextView
    private lateinit var tv_totalAmount: TextView
    private lateinit var shoppingAdapter: ShoppingAdapter

    val viewModel by lazy {
        ViewModelProvider(this).get(SIMOfferViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_sim_offer, null, false);
        initView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObserve()
        showLoading()
        viewModel.getGoods(OffersRequest("offers", "globegomo"))
    }

    override fun initView(view: View) {
        super.initView(view)
        rv_list = view.findViewById(R.id.activity_sim_offer_list_rv)
        val layoutManager = GridLayoutManager(context, 2)
        rv_list.layoutManager = layoutManager
        adapter = GoodsAdapter(viewModel.goodList, object : GoodsAdapter.GoodCallback {
            override fun addShopping(good: Good) {
                viewModel.addShoppingData(good)
            }

            override fun gotoDetail(good: Good) {
                val args: Bundle = Bundle()
                args.putParcelable("good", good)
                findNavController().navigate(R.id.action_SIMOfferFragment_to_DetailFragment, args)

            }
        })
        rv_list.adapter = adapter
        rv_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val index: Int = parent.indexOfChild(view)
                if (index % 2 == 0) {
                    // first columns
                    outRect.right = 20
                }
                outRect.bottom = 20
            }
        })


        val shoppingListGroup: Group =
            view.findViewById(R.id.activity_sim_offer_shopping_list_group)
        val iv_shopping: ImageView = view.findViewById(R.id.activity_sim_offer_shopping_iv)
        iv_shopping.setOnClickListener {
            if (shoppingListGroup.visibility != View.VISIBLE) {
                shoppingListGroup.visibility = View.VISIBLE
            } else {
                shoppingListGroup.visibility = View.GONE
            }
        }
        tv_selectNum = view.findViewById(R.id.activity_sim_offer_shopping_select_num_tv)
        tv_selectNum.text = "0"
        tv_totalAmount = view.findViewById(R.id.activity_sim_offer_shopping_total_amount_tv)
        tv_totalAmount.text = "₱0"

        val tv_checkout: TextView = view.findViewById(R.id.activity_sim_offer_shopping_checkout_tv)
        tv_checkout.setOnClickListener {
            Toast.makeText(SIMApplication.context, "结算商品", Toast.LENGTH_SHORT).show()
        }

        val rv_shoppingList: RecyclerView =
            view.findViewById(R.id.activity_sim_offer_shopping_list_rv)
        rv_shoppingList.layoutManager = LinearLayoutManager(context)
        shoppingAdapter = ShoppingAdapter(viewModel.shoppingList.value, viewModel)
        rv_shoppingList.adapter = shoppingAdapter

        val tv_clear: TextView = view.findViewById(R.id.activity_sim_offer_shopping_clear_tv)
        tv_clear.setOnClickListener {
            viewModel.clearShoppingGoods()
        }

    }

    private fun addObserve() {
        viewModel.goodLiveData.observe(this, androidx.lifecycle.Observer { result ->
            Log.d(TAG, "addObserve goodLiveData observe result:${result}")
            val goods = result.getOrNull()
            Log.d(TAG, "addObserve goodLiveData observe goods:${goods}")
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
            Log.d(TAG, "addObserve shoppingList observe it:${it}")
            tv_selectNum.text = viewModel.getSelectNum().toString()
            tv_totalAmount.text = "₱" + viewModel.getTotalAmount()
            shoppingAdapter.shoppingGoods = viewModel.shoppingList.value
            shoppingAdapter.notifyDataSetChanged()
        })
    }
}
