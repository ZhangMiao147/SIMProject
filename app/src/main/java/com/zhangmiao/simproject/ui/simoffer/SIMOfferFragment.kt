package com.zhangmiao.simproject.ui.simoffer

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.common.ui.BaseFragment
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.OffersRequest
import com.zhangmiao.simproject.ui.detail.DetailFragment

class SIMOfferFragment : BaseFragment() {

    private lateinit var goodsAdapter: GoodsAdapter
    private lateinit var rv_list: RecyclerView
    private lateinit var tv_selectNum: TextView
    private lateinit var tv_totalAmount: TextView
    private lateinit var shoppingAdapter: ShoppingAdapter
    private lateinit var cb_selectAll: CheckBox
    private lateinit var srl_refreshLayout: SwipeRefreshLayout

    private val MAX_CART_NUM = 3
    private val GOOD_LIST_COLUMN_COUNT = 2

    val viewModel by lazy {
        ViewModelProvider(this).get(SIMOfferViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_sim_offer, null, false);
        initView(view)
        if (viewModel.goodList.isNullOrEmpty()) {
            showLoading()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObserve()
        viewModel.getGoods(OffersRequest("offers", "globegomo"))
        viewModel.getShooingGoodList()
    }

    override fun initView(view: View) {
        super.initView(view)
        rv_list = view.findViewById(R.id.fragment_sim_offer_list_rv)
        val shoppingListGroup: Group =
            view.findViewById(R.id.fragment_sim_offer_shopping_list_group)

        val layoutManager = GridLayoutManager(context, GOOD_LIST_COLUMN_COUNT)
        rv_list.layoutManager = layoutManager
        goodsAdapter = GoodsAdapter(viewModel.goodList, object : GoodsAdapter.GoodCallback {
            override fun addShopping(good: Good) {
                viewModel.addShoppingData(good)
                Toast.makeText(
                    context,
                    getString(R.string.good_add_card, good.name),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun gotoDetail(good: Good) {
                if (shoppingListGroup.visibility == View.GONE) {
                    val args = Bundle()
                    args.putParcelable(DetailFragment.ARGUMENTS_GOOD, good)
                    findNavController().navigate(
                        R.id.action_SIMOfferFragment_to_DetailFragment,
                        args
                    )
                }
            }
        })
        rv_list.adapter = goodsAdapter
        rv_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val index: Int = parent.indexOfChild(view)
                if (index % GOOD_LIST_COLUMN_COUNT == 0) {
                    // first columns
                    outRect.right = resources.getDimensionPixelSize(R.dimen.dimen_20)
                }
                outRect.bottom = resources.getDimensionPixelSize(R.dimen.dimen_20)
            }
        })

        val iv_shopping: ImageView = view.findViewById(R.id.fragment_sim_offer_shopping_iv)
        iv_shopping.setOnClickListener {
            if (shoppingListGroup.visibility != View.VISIBLE) {
                if (viewModel.shoppingGoodList.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.cart_no_items), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    shoppingListGroup.visibility = View.VISIBLE
                }
            } else {
                shoppingListGroup.visibility = View.GONE
            }
        }
        tv_selectNum = view.findViewById(R.id.fragment_sim_offer_shopping_select_num_tv)
        tv_selectNum.text = viewModel.getSelectNum().toString()
        tv_totalAmount = view.findViewById(R.id.fragment_sim_offer_shopping_total_amount_tv)
        tv_totalAmount.text = getString(R.string.price, viewModel.getTotalAmount())

        val tv_checkout: TextView = view.findViewById(R.id.fragment_sim_offer_shopping_checkout_tv)
        tv_checkout.setOnClickListener {
            val selectNum = viewModel.getSelectNum()
            if (selectNum > MAX_CART_NUM) {
                Toast.makeText(
                    SIMApplication.context,
                    getString(R.string.cart_check_tip),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    SIMApplication.context,
                    getString(R.string.check_finish),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val rv_shoppingList: RecyclerView =
            view.findViewById(R.id.fragment_sim_offer_shopping_list_rv)
        rv_shoppingList.layoutManager = LinearLayoutManager(context)
        shoppingAdapter = ShoppingAdapter(viewModel.shoppingGoodList, viewModel)
        rv_shoppingList.adapter = shoppingAdapter

        val tv_clear: TextView = view.findViewById(R.id.fragment_sim_offer_shopping_clear_tv)
        tv_clear.setOnClickListener {
            viewModel.clearShoppingGoods()
        }

        cb_selectAll = view.findViewById(R.id.fragment_sim_offer_shopping_select_all_cb)
        cb_selectAll.setOnClickListener {
            viewModel.changeSelectAllShoppingGoods(cb_selectAll.isChecked)
        }

        srl_refreshLayout = view.findViewById(R.id.fragment_sim_offer_refresh_layout_srl)
        srl_refreshLayout.setColorSchemeColors(androidx.appcompat.R.color.material_blue_grey_800)
        srl_refreshLayout.setProgressBackgroundColorSchemeResource(androidx.constraintlayout.widget.R.color.material_blue_grey_800)
        srl_refreshLayout.setOnRefreshListener {
            hideTipView()
            viewModel.getGoods(OffersRequest("offers", "globegomo"))
        }

    }


    private fun addObserve() {
        viewModel.goodLiveData.observe(this, androidx.lifecycle.Observer { result ->
            Log.d(TAG, "addObserve goodLiveData observe result:${result}")
            val goods = result.getOrNull()
            Log.d(TAG, "addObserve goodLiveData observe goods:${goods}")
            hideLoading()
            srl_refreshLayout.isRefreshing = false
            if (!goods.isNullOrEmpty()) {
                viewModel.goodList.clear()
                viewModel.goodList.addAll(goods)
                goodsAdapter.goodsList = goods
                goodsAdapter.notifyDataSetChanged()
            } else {
                showTipView(getString(R.string.data_exception_tip))
            }

        })

        viewModel.shoppingGoodLiveData.observe(this, Observer {
            Log.d(TAG, "addObserve shoppingGoodLiveData observe it:${it}")
            viewModel.shoppingGoodList.clear()
            viewModel.shoppingGoodList.addAll(it)
            tv_selectNum.text = viewModel.getSelectNum().toString()
            tv_totalAmount.text = getString(R.string.price, viewModel.getTotalAmount())
            shoppingAdapter.shoppingGoods = viewModel.shoppingGoodList
            shoppingAdapter.notifyDataSetChanged()
            cb_selectAll.isChecked = viewModel.isSelectAllShoppingGoods()
        })
    }
}
