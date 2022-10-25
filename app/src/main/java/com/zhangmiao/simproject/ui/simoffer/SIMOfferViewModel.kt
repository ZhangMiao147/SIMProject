package com.zhangmiao.simproject.ui.simoffer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zhangmiao.simproject.logic.Repository
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.OffersRequest
import com.zhangmiao.simproject.logic.model.ShoppingGood

class SIMOfferViewModel : ViewModel() {

    /* ------ 商品列表 ------ */
    private val getLiveData = MutableLiveData<OffersRequest>()

    val goodList = ArrayList<Good>()

    val goodLiveData = Transformations.switchMap(this.getLiveData){ offersRequest ->
        Log.d("SIMOfferViewModel","switchMap getOffers")
        Repository.getOffers(offersRequest.type, offersRequest.operator_name)
    }

    fun getGoods(offersRequest: OffersRequest) {
        getLiveData.value = offersRequest
    }

    /* ------- 购物车 --------- */
    val shoppingList = MutableLiveData<ArrayList<ShoppingGood>>()


    fun addShoppingData(good: Good) {
        var shoppingGoods = shoppingList.value
        val shoppingGood =
            ShoppingGood(good.id, good.name, good.amount_final, 1, true)
        val index = shoppingGoods?.indexOf(shoppingGood) ?: -1
        if (shoppingGoods == null){
            shoppingGoods = ArrayList<ShoppingGood>()
        }
        if (index != -1) {
            val oldGood = shoppingGoods.get(index)
            oldGood.num++
        } else {
            shoppingGoods.add(shoppingGood)
        }
    }

    fun addShoppingGoodNum(goodId: String) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            it.num++
        }
    }

    fun reduceShoppingGoodNum(goodId: String) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            it.num--
        }
    }

    fun getSelectNum(): Int {
        var num = 0
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (it.select) {
                num += it.num
            }
        }
        return num
    }

    fun getTotalAmount(): Int {
        var totalAmount = 0
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (it.select) {
                totalAmount += it.amount
            }
        }
        return totalAmount
    }

    fun clearShoppingGoods() {
        shoppingList.value?.clear()
    }

    fun selectAllShoppingGoods() {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            it.select = true
        }
    }


}