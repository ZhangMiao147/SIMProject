package com.zhangmiao.simproject.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.logic.dao.CartGoodsDao
import com.zhangmiao.simproject.logic.dao.CartGoodsDatabase
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
            val goods: List<Goods> = switchToGoods(offersResponse)
            Log.d(TAG, "getOffers goods:${goods}")
            Result.success(goods)
        } catch (e: Exception) {
            Result.failure<List<Goods>>(e)
        }
        emit(result)
    }

    /**
     * network data switch show data
     */
    private fun switchToGoods(getOffersResponse: GetOffersResponse): ArrayList<Goods> {
        val goodsList: ArrayList<Goods> = ArrayList()
        val gson = Gson()
        val offerById: String = gson.toJson(getOffersResponse.offer_by_id)
        try {
            val offerByIdObject = JSONObject(offerById)
            val keys = offerByIdObject.keys()
            keys.forEach {
                val offerObject = offerByIdObject.getJSONObject(it)
                val offer: Offer = gson.fromJson(offerObject.toString(), Offer::class.java)
                val regularPrice = offer.data.regular_price
                var goodRegularPrice = Goods.REGULAR_PRICE_NO
                if (!regularPrice.isNullOrEmpty()) {
                    goodRegularPrice = regularPrice.toInt()
                }
                val goods = Goods(
                    offer.id,
                    offer.data.name,
                    goodRegularPrice,
                    offer.data.amounts.primary.toInt(),
                    offer.data.description,
                    offer.data.Detail ?: ""

                )
                goodsList.add(goods)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return goodsList
    }

    private var cartGoodDao: CartGoodsDao? = null

    init {
        cartGoodDao = CartGoodsDatabase.getDatabase(SIMApplication.context).cartGoodsDao()
        Log.d(TAG, "init cartGoodDao:${cartGoodDao}")
    }

    fun saveCartGoods(cartGoods: CartGoods) {
        GlobalScope.launch {
            Log.d(TAG, "saveCartGoods shoppingGoodDao:${cartGoodDao}")
            cartGoodDao?.insertCartGoods(cartGoods)
        }
    }

    fun updateCartGoods(cartGoods: CartGoods) {
        GlobalScope.launch {
            cartGoodDao?.updateCartGoods(cartGoods)
        }
    }

    fun getCartGoodsList(): LiveData<List<CartGoods>>? {
        return cartGoodDao?.getCartGoodsList()
    }

    fun deleteAllCartGoods() {
        GlobalScope.launch {
            cartGoodDao?.deleteAllCartGoods()
        }
    }

    fun deleteCartGoods(cartGoods: CartGoods) {
        GlobalScope.launch {
            cartGoodDao?.deleteCartGoods(cartGoods)
        }
    }

}