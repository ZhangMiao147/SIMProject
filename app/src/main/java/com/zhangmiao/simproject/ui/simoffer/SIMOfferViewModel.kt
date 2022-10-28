package com.zhangmiao.simproject.ui.simoffer

import androidx.lifecycle.*
import com.zhangmiao.simproject.logic.Repository
import com.zhangmiao.simproject.logic.model.CartGoods
import com.zhangmiao.simproject.logic.model.Goods
import com.zhangmiao.simproject.logic.model.GoodsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SIMOfferViewModel : ViewModel() {

    val TAG = SIMOfferViewModel::class.simpleName

    init {
        getCartGoodsList()
        getGoods()
    }

    val goodsList = ArrayList<Goods>()

    val  _goodsListLiveData: MutableLiveData<GoodsListResponse> = MutableLiveData()
    val  goodsListLiveData: LiveData<GoodsListResponse> = _goodsListLiveData

    fun getGoods() {
        viewModelScope.launch {
            _goodsListLiveData.value = Repository.getOffers("offers", "globegomo")
        }
    }

    val cartGoodsList = ArrayList<CartGoods>()

    val  _cartGoodsLiveData: MutableLiveData<List<CartGoods>> = MutableLiveData()
    val  cartGoodsLiveData: LiveData<List<CartGoods>> = _cartGoodsLiveData

    private fun getCartGoodsList() {
        viewModelScope.launch (Dispatchers.IO){
            _cartGoodsLiveData.value = Repository.getCartGoodsList()
        }
    }

    fun addCartGoodsData(good: Goods) {
        viewModelScope.launch(Dispatchers.IO) {
            val cartGoods = CartGoods(good.id, good.name, good.amount_primary, 1, true)
            val index = cartGoodsList.indexOf(cartGoods)
            if (index == -1) {
                Repository.saveCartGoods(cartGoods)
            } else {
                val oldCartGoods = cartGoodsList.get(index)
                cartGoods.num = oldCartGoods.num + 1
                Repository.updateCartGoods(cartGoods)
            }
        }
    }

    fun addCartGoodsNum(goodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cartGoodsList.forEach {
                if (it.id == goodId) {
                    it.num++
                    Repository.updateCartGoods(it)
                }
            }
        }
    }

    fun reduceCartGoodsNum(goodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cartGoodsList.forEach {
                if (it.id == goodId) {
                    it.num--
                    Repository.updateCartGoods(it)
                }
            }
        }
    }

    fun getCartSelectNum(): Int {
        var num = 0
        cartGoodsList.forEach {
            if (it.select) {
                num += it.num
            }
        }
        return num
    }

    fun getCartTotalAmount(): Int {
        var totalAmount = 0
        cartGoodsList.forEach {
            if (it.select) {
                totalAmount += it.amount * it.num
            }
        }
        return totalAmount
    }

    fun changeCartGoodsSelect(goodId: String, select: Boolean) {
        viewModelScope.launch(Dispatchers.IO){
            cartGoodsList.forEach {
                if (it.id == goodId) {
                    it.select = select
                    Repository.updateCartGoods(it)
                }
            }
        }

    }

    fun clearCartGoods() {
        viewModelScope.launch(Dispatchers.IO){
            Repository.deleteAllCartGoods()
        }
    }

    fun clearCartSelectGoods() {
        viewModelScope.launch(Dispatchers.IO){
            Repository.deleteCartSelectGoods()
        }
    }

    fun changeSelectAllCartGoods(select: Boolean) {
        viewModelScope.launch(Dispatchers.IO){
            cartGoodsList.forEach {
                it.select = select
                Repository.updateCartGoods(it)
            }
        }
    }

    fun isSelectAllCartGoods(): Boolean {
        cartGoodsList.forEach {
            if (!it.select) {
                return false
            }
        }
        return true
    }


}