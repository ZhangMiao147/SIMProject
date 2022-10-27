package com.zhangmiao.simproject.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.logic.dao.ShoppingGoodDao
import com.zhangmiao.simproject.logic.dao.ShoppingGoodDatabase
import com.zhangmiao.simproject.logic.model.*
import com.zhangmiao.simproject.logic.network.OffersNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object Repository {

    private val TAG = "Repository"

    fun getOffers(type: String, operator_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val offersResponse = OffersNetwork.getOffers(OffersRequest(type, operator_name))
            Log.d(TAG, "getOffers offersResponse:${offersResponse}")
            val goods: List<Good> = switchToGoods(offersResponse)
            Log.d(TAG, "getOffers goods:${goods}")
            Result.success(goods)
        } catch (e: Exception) {
            Result.failure<List<Good>>(e)
        }
        emit(result)
    }

    /**
     * network data switch show data
     */
    private fun switchToGoods(getOffersResponse: GetOffersResponse): ArrayList<Good> {
        val goods: ArrayList<Good> = ArrayList()
        val gson = Gson()
        val offerById: String = gson.toJson(getOffersResponse.offer_by_id)
        try {
            val offerByIdObject = JSONObject(offerById)
            val keys = offerByIdObject.keys()
            keys.forEach {
                val offerObject = offerByIdObject.getJSONObject(it)
                val offer: Offer = gson.fromJson(offerObject.toString(), Offer::class.java)
                val regularPrice = offer.data.regular_price
                var goodRegularPrice = Good.REGULAR_PRICE_NO
                if (!regularPrice.isNullOrEmpty()) {
                    goodRegularPrice = regularPrice.toInt()
                }
                val good = Good(
                    offer.id,
                    offer.data.name,
                    goodRegularPrice,
                    offer.data.amounts.primary.toInt(),
                    offer.data.description,
                    offer.data.Detail ?: ""

                )
                goods.add(good)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return goods
    }

    private var shoppingGoodDao: ShoppingGoodDao? = null

    init {
        shoppingGoodDao = ShoppingGoodDatabase.getDatabase(SIMApplication.context).shoppingGoodDao()
        Log.d(TAG, "init shoppingGoodDao:${shoppingGoodDao}")
    }

    fun saveShoppingGood(shoppingGood: ShoppingGood) {
        GlobalScope.launch {
            Log.d(TAG, "saveShoppingGood shoppingGoodDao:${shoppingGoodDao}")
            shoppingGoodDao?.insertShoppingGood(shoppingGood)
        }
    }

    fun updateShoppingGood(shoppingGood: ShoppingGood) {
        GlobalScope.launch {
            shoppingGoodDao?.updateShoppingGood(shoppingGood)
        }
    }

    fun getShoppingGoodList(): LiveData<List<ShoppingGood>>? {
        return shoppingGoodDao?.getShoppingGoodList()
    }

    fun deleteAllShoppingGood() {
        GlobalScope.launch {
            shoppingGoodDao?.deleteAllShoppingGood()
        }
    }

    fun deleteShoppingGood(shoppingGood: ShoppingGood) {
        GlobalScope.launch {
            shoppingGoodDao?.deleteShoppingGood(shoppingGood)
        }
    }

}