package com.zhangmiao.simproject.ui.simoffer

import android.util.Log
import androidx.lifecycle.*
import com.zhangmiao.simproject.logic.Repository
import com.zhangmiao.simproject.logic.model.CartGoods
import com.zhangmiao.simproject.logic.model.Goods
import com.zhangmiao.simproject.logic.model.OffersRequest

class SIMOfferViewModel : ViewModel() {

    val TAG = SIMOfferViewModel::class.simpleName

    private val getLiveData = MutableLiveData<OffersRequest>()

    val goodsList = ArrayList<Goods>()

    val goodsLiveData = Transformations.switchMap(this.getLiveData) { offersRequest ->
        Log.d(TAG, "switchMap getOffers")
        Repository.getOffers(offersRequest.type, offersRequest.operator_name)
    }

    fun getGoods(offersRequest: OffersRequest) {
        getLiveData.value = offersRequest
    }

    val cartGoodsList = ArrayList<CartGoods>()

    private val getCartGoods = MutableLiveData<String>()

    val cartGoodsLiveData = Transformations.switchMap(getCartGoods) {
        Log.d(TAG, "cartGoodsLiveData switchMap it:${it}")
        Repository.getCartGoodsList()
    }

    fun getCartGoodsList() {
        getCartGoods.value = System.currentTimeMillis().toString()
    }


    fun addCartGoodsData(good: Goods) {
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

    fun addCartGoodsNum(goodId: String) {
        cartGoodsList.forEach {
            if (it.id == goodId) {
                it.num++
                Repository.updateCartGoods(it)
            }
        }
    }

    fun reduceCartGoodsNum(goodId: String) {
        cartGoodsList.forEach {
            if (it.id == goodId) {
                it.num--
                Repository.updateCartGoods(it)
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
        cartGoodsList.forEach {
            if (it.id == goodId) {
                it.select = select
                Repository.updateCartGoods(it)
            }
        }
    }

    fun clearCartGoods() {
        Repository.deleteAllCartGoods()
    }

    fun clearCartSelectGoods() {
        Repository.deleteCartSelectGoods()
    }

    fun changeSelectAllCartGoods(select: Boolean) {
        cartGoodsList.forEach {
            it.select = select
            Repository.updateCartGoods(it)
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