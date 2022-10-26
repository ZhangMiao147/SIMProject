package com.zhangmiao.simproject.ui.simoffer

import android.util.Log
import androidx.lifecycle.*
import com.zhangmiao.simproject.logic.Repository
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.OffersRequest
import com.zhangmiao.simproject.logic.model.ShoppingGood

class SIMOfferViewModel : ViewModel() {

    val TAG = SIMOfferViewModel::class.simpleName

    /* ------ 商品列表 ------ */
    private val getLiveData = MutableLiveData<OffersRequest>()

    val goodList = ArrayList<Good>()

    val goodLiveData = Transformations.switchMap(this.getLiveData) { offersRequest ->
        Log.d(TAG, "switchMap getOffers")
        Repository.getOffers(offersRequest.type, offersRequest.operator_name)
    }

    fun getGoods(offersRequest: OffersRequest) {
        getLiveData.value = offersRequest
    }

//    private val getShoppingGoodLiveData = MutableLiveData<String>()

//    val shoppingGoodLiveData = Transformations.switchMap(this.getShoppingGoodLiveData) { value ->
//        Log.d(TAG, "switchMap shoppingGoodLiveData")
//        Repository.getShoppingGoodList()
//    }

    /* ------- 购物车 --------- */
    val shoppingList = MutableLiveData<ArrayList<ShoppingGood>>()

    val shoppingGoodList = ArrayList<ShoppingGood>()

//    fun getShoppingGoodList(){
//        Log.d(TAG, "getShoppingGoodList")
//        val shoppingGoodList = Repository.getShoppingGoodList()
//        Log.d(TAG, "getShoppingGoodList shoppingGoodList：${shoppingGoodList?.value}")
//        shoppingGoodList?.value?.let {
//            var shoppingGoodList:ArrayList<ShoppingGood>? = shoppingList?.value
//            if (shoppingGoodList == null) shoppingGoodList = ArrayList()
//            it.forEach {
//                shoppingGoodList.add(it)
//            }
//            shoppingList.value = shoppingGoodList
//        }
//    }


    fun addShoppingData(good: Good) {
        var shoppingGoods = shoppingList.value
        val shoppingGood =
            ShoppingGood(good.id, good.name, good.amount_final, 1, true)

        val index = shoppingGoods?.indexOf(shoppingGood) ?: -1
        if (shoppingGoods == null) {
            shoppingGoods = ArrayList<ShoppingGood>()
        }
        if (index != -1) {
            val oldGood = shoppingGoods.get(index)
            oldGood.num++
//            Repository.updateShoppingGood(oldGood)
        } else {
            shoppingGoods.add(shoppingGood)
//            Repository.saveShoppingGood(shoppingGood)
        }
        shoppingList.value = shoppingGoods
    }

    fun addShoppingGoodNum(goodId: String) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (it.id == goodId) {
                it.num++
//                Repository.updateShoppingGood(it)
            }
        }
        shoppingList.value = shoppingGoods
    }

    fun reduceShoppingGoodNum(goodId: String) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (it.id == goodId) {
                it.num--
//                Repository.updateShoppingGood(it)
            }
        }
        shoppingList.value = shoppingGoods
    }

    fun getSelectNum(): Int {
        var num = 0
//        val shoppingGoods = shoppingList.value
//        shoppingGoods?.forEach {
//            if (it.select) {
//                num += it.num
//            }
//        }
        shoppingGoodList.forEach {
            if (it.select) {
                num += it.num
            }
        }
        return num
    }

    fun getTotalAmount(): Int {
        var totalAmount = 0
//        val shoppingGoods = shoppingList.value
//        shoppingGoods?.forEach {
//            if (it.select) {
//                totalAmount += it.amount * it.num
//            }
//        }
        shoppingGoodList.forEach {
            if (it.select) {
                totalAmount += it.amount * it.num
            }
        }
        return totalAmount
    }

    fun changeShoppingGoodSelect(goodId: String, select: Boolean) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (it.id == goodId) {
                it.select = select
//                Repository.updateShoppingGood(it)
            }
        }
        shoppingList.value = shoppingGoods
    }

    fun clearShoppingGoods() {
        shoppingList.value?.clear()
        shoppingList.value = ArrayList()
//        Repository.deleteAllShoppingGood()
    }

    fun changeSelectAllShoppingGoods(select: Boolean) {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            it.select = select
//            Repository.updateShoppingGood(it)
        }
        shoppingList.value = shoppingGoods
    }

    fun isSelectAllShoppingGoods(): Boolean {
        val shoppingGoods = shoppingList.value
        shoppingGoods?.forEach {
            if (!it.select) {
                return false
            }
        }
        return true
    }


}