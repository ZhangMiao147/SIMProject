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
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zhangmiao.simproject.R
import com.zhangmiao.simproject.common.extension.showToast
import com.zhangmiao.simproject.common.ui.BaseFragment
import com.zhangmiao.simproject.logic.model.Goods
import com.zhangmiao.simproject.logic.model.GoodsListResponse
import com.zhangmiao.simproject.ui.detail.DetailFragment

class SIMOfferFragment : BaseFragment() {

    private val MAX_CART_NUM = 3
    private val GOOD_LIST_COLUMN_COUNT = 2

    private lateinit var rv_goodsList: RecyclerView
    private lateinit var tv_selectNum: TextView
    private lateinit var tv_totalAmount: TextView
    private lateinit var cb_selectAll: CheckBox
    private lateinit var srl_refreshLayout: SwipeRefreshLayout

    private lateinit var goodsAdapter: GoodsAdapter
    private lateinit var cartAdapter: CartAdapter

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
        if (viewModel.goodsList.isNullOrEmpty()) {
            showLoading()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObserve()
    }

    override fun initView(view: View) {
        super.initView(view)
        rv_goodsList = view.findViewById(R.id.fragment_sim_offer_goods_list_rv)
        val cartListGroup: Group =
            view.findViewById(R.id.fragment_sim_offer_cart_list_group)

        val layoutManager = GridLayoutManager(context, GOOD_LIST_COLUMN_COUNT)
        rv_goodsList.layoutManager = layoutManager
        goodsAdapter = GoodsAdapter(viewModel.goodsList, object : GoodsAdapter.GoodsCallback {
            override fun addCartGoods(goods: Goods) {
                viewModel.addCartGoodsData(goods)
                getString(R.string.goods_add_card, goods.name).showToast()
            }

            override fun gotoDetail(goods: Goods) {
                if (cartListGroup.visibility == View.GONE) {
                    val args = Bundle()
                    args.putParcelable(DetailFragment.ARGUMENTS_GOODS, goods)
                    findNavController().navigate(
                        R.id.action_SIMOfferFragment_to_DetailFragment,
                        args
                    )
                } else {
                    cartListGroup.visibility = View.GONE
                }
            }
        })
        rv_goodsList.adapter = goodsAdapter
        rv_goodsList.addItemDecoration(object : RecyclerView.ItemDecoration() {
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

        val iv_cart: ImageView = view.findViewById(R.id.fragment_sim_offer_cart_iv)
        iv_cart.setOnClickListener {
            if (cartListGroup.visibility != View.VISIBLE) {
                if (viewModel.cartGoodsList.isNullOrEmpty()) {
                    getString(R.string.cart_no_items).showToast()
                } else {
                    cartListGroup.visibility = View.VISIBLE
                }
            } else {
                cartListGroup.visibility = View.GONE
            }
        }
        tv_selectNum = view.findViewById(R.id.fragment_sim_offer_cart_select_num_tv)
        tv_selectNum.text = viewModel.getCartSelectNum().toString()
        tv_totalAmount = view.findViewById(R.id.fragment_sim_offer_cart_total_amount_tv)
        tv_totalAmount.text = getString(R.string.price, viewModel.getCartTotalAmount())

        val tv_checkout: TextView = view.findViewById(R.id.fragment_sim_offer_cart_checkout_tv)
        tv_checkout.setOnClickListener {
            val selectNum = viewModel.getCartSelectNum()
            if (selectNum == 0) {
                getString(R.string.cart_no_items).showToast()
            } else if (selectNum > MAX_CART_NUM) {
                getString(R.string.cart_check_tip).showToast()
            } else {
                viewModel.clearCartSelectGoods()
                getString(R.string.check_finish).showToast()
            }
        }

        val rv_cartList: RecyclerView =
            view.findViewById(R.id.fragment_sim_offer_cart_list_rv)
        rv_cartList.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(viewModel.cartGoodsList, object : CartAdapter.CartCallback {
            override fun changeCartGoodsSelect(id: String, select: Boolean) {
                viewModel.changeCartGoodsSelect(id, select)
            }

            override fun reduceCartGoodsNum(id: String) {
                viewModel.reduceCartGoodsNum(id)
            }

            override fun addCartGoodsNum(id: String) {
                viewModel.addCartGoodsNum(id)
            }
        })
        rv_cartList.adapter = cartAdapter

        val tv_clear: TextView = view.findViewById(R.id.fragment_sim_offer_cart_clear_tv)
        tv_clear.setOnClickListener {
            viewModel.clearCartGoods()
        }

        cb_selectAll = view.findViewById(R.id.fragment_sim_offer_cart_select_all_cb)
        cb_selectAll.setOnClickListener {
            viewModel.changeSelectAllCartGoods(cb_selectAll.isChecked)
        }

        srl_refreshLayout = view.findViewById(R.id.fragment_sim_offer_refresh_layout_srl)
        srl_refreshLayout.setColorSchemeColors(androidx.appcompat.R.color.material_blue_grey_800)
        srl_refreshLayout.setProgressBackgroundColorSchemeResource(androidx.constraintlayout.widget.R.color.material_blue_grey_800)
        srl_refreshLayout.setOnRefreshListener {
            hideTipView()
            viewModel.getGoods()
        }

    }

    private fun addObserve() {
        viewModel.goodsListLiveData.observe(this, androidx.lifecycle.Observer { result ->
            Log.d(TAG, "addObserve goodLiveData observe result:${result}")
            hideLoading()
            srl_refreshLayout.isRefreshing = false
            when (result) {
                is GoodsListResponse.Response -> {
                    val goodsList = result.goodsList
                    if (!goodsList.isNullOrEmpty()) {
                        viewModel.goodsList.clear()
                        viewModel.goodsList.addAll(goodsList)
                        goodsAdapter.goodsList = goodsList
                        goodsAdapter.notifyDataSetChanged()
                    } else {
                        showTipView(getString(R.string.data_empty_tip))
                    }
                }
                is GoodsListResponse.Failure -> {
                    showTipView(getString(R.string.data_exception_tip))
                }
                else -> {}
            }
        })

        viewModel.cartGoodsLiveData.observe(this, Observer {
            Log.d(TAG, "addObserve shoppingGoodLiveData observe it:${it}")
            viewModel.cartGoodsList.clear()
            viewModel.cartGoodsList.addAll(it)
            tv_selectNum.text = viewModel.getCartSelectNum().toString()
            tv_totalAmount.text = getString(R.string.price, viewModel.getCartTotalAmount())
            cartAdapter.cartGoods = viewModel.cartGoodsList
            cartAdapter.notifyDataSetChanged()
            cb_selectAll.isChecked = viewModel.isSelectAllCartGoods()
        })
    }
}
