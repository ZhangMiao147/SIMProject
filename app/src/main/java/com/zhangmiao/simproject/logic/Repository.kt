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

    suspend fun getOffers(type: String, operator_name: String): GoodsListResponse {
        return try {
            val offersResponse = OffersNetwork.getOffers(OffersRequest(type, operator_name))
            Log.d(TAG, "getOffers offersResponse:${offersResponse}")
            val goods: List<Goods> = switchToGoods(offersResponse)
            Log.d(TAG, "getOffers goods:${goods}")
            GoodsListResponse.Response(goods)
        } catch (e: Exception) {
            GoodsListResponse.Failure
        }
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
                val goods = Goods(
                    offer.id,
                    offer.data.name,
                    with(offer.data.regular_price) {
                        if (this.isNullOrEmpty()) {
                            Goods.REGULAR_PRICE_NO
                        } else {
                            toInt()
                        }
                    },
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

    suspend fun saveCartGoods(cartGoods: CartGoods) {
        Log.d(TAG, "saveCartGoods shoppingGoodDao:${cartGoodDao}")
        cartGoodDao?.insertCartGoods(cartGoods)
    }

    suspend fun updateCartGoods(cartGoods: CartGoods) {
        cartGoodDao?.updateCartGoods(cartGoods)
    }

    suspend fun getCartGoodsList(): List<CartGoods>? {
        return cartGoodDao?.getCartGoodsList()
    }

    suspend fun deleteAllCartGoods() {
        cartGoodDao?.deleteAllCartGoods()
    }

    suspend fun deleteCartSelectGoods() {
        cartGoodDao?.deleteCartGoods()
    }

    suspend fun deleteCartGoods(cartGoods: CartGoods) {
        cartGoodDao?.deleteCartGoods(cartGoods)
    }

}