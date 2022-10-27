package com.zhangmiao.simproject.ui.simoffer

import android.util.Log
import androidx.lifecycle.*
import com.zhangmiao.simproject.logic.Repository
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.OffersRequest
import com.zhangmiao.simproject.logic.model.ShoppingGood

class SIMOfferViewModel : ViewModel() {

    val TAG = SIMOfferViewModel::class.simpleName
    
    private val getLiveData = MutableLiveData<OffersRequest>()

    val goodList = ArrayList<Good>()

    val goodLiveData = Transformations.switchMap(this.getLiveData) { offersRequest ->
        Log.d(TAG, "switchMap getOffers")
        Repository.getOffers(offersRequest.type, offersRequest.operator_name)
    }

    fun getGoods(offersRequest: OffersRequest) {
        getLiveData.value = offersRequest
    }

    val shoppingGoodList = ArrayList<ShoppingGood>()

    private val getShoppingGood = MutableLiveData<String>()

    val shoppingGoodLiveData = Transformations.switchMap(getShoppingGood) {
        Log.d(TAG, "shoppingGoodLiveData switchMap it:${it}")
        Repository.getShoppingGoodList()
    }

    fun getShooingGoodList() {
        getShoppingGood.value = System.currentTimeMillis().toString()
    }


    fun addShoppingData(good: Good) {
        val shoppingGood = ShoppingGood(good.id, good.name, good.amount_primary, 1, true)
        val index = shoppingGoodList.indexOf(shoppingGood)
        if (index == -1) {
            Repository.saveShoppingGood(shoppingGood)
        } else {
            val oldShoppingGood = shoppingGoodList.get(index)
            shoppingGood.num = oldShoppingGood.num + 1
            Repository.updateShoppingGood(shoppingGood)
        }
    }

    fun addShoppingGoodNum(goodId: String) {
        shoppingGoodList.forEach {
            if (it.id == goodId) {
                it.num++
                Repository.updateShoppingGood(it)
            }
        }
    }

    fun reduceShoppingGoodNum(goodId: String) {
        shoppingGoodList.forEach {
            if (it.id == goodId) {
                it.num--
                Repository.updateShoppingGood(it)
            }
        }
    }

    fun getSelectNum(): Int {
        var num = 0
        shoppingGoodList.forEach {
            if (it.select) {
                num += it.num
            }
        }
        return num
    }

    fun getTotalAmount(): Int {
        var totalAmount = 0
        shoppingGoodList.forEach {
            if (it.select) {
                totalAmount += it.amount * it.num
            }
        }
        return totalAmount
    }

    fun changeShoppingGoodSelect(goodId: String, select: Boolean) {
        shoppingGoodList.forEach {
            if (it.id == goodId) {
                it.select = select
                Repository.updateShoppingGood(it)
            }
        }
    }

    fun clearShoppingGoods() {
        Repository.deleteAllShoppingGood()
    }

    fun changeSelectAllShoppingGoods(select: Boolean) {
        shoppingGoodList.forEach {
            it.select = select
            Repository.updateShoppingGood(it)
        }
    }

    fun isSelectAllShoppingGoods(): Boolean {
        shoppingGoodList.forEach {
            if (!it.select) {
                return false
            }
        }
        return true
    }


}