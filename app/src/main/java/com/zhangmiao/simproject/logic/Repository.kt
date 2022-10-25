package com.zhangmiao.simproject.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.zhangmiao.simproject.logic.model.GetOffersResponse
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.model.Offer
import com.zhangmiao.simproject.logic.model.OffersRequest
import com.zhangmiao.simproject.logic.network.OffersNetwork
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

object Repository {

    val TAG = "Repository"

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
    fun switchToGoods(getOffersResponse: GetOffersResponse): ArrayList<Good> {
        val goods: ArrayList<Good> = ArrayList()
        val gson = Gson()
        val offerById: String = gson.toJson(getOffersResponse.offer_by_id)
        try {
            val offerByIdObject = JSONObject(offerById)
            val keys = offerByIdObject.keys()
            keys.forEach {
                val offerObject = offerByIdObject.getJSONObject(it)
                val offer: Offer = gson.fromJson(offerObject.toString(), Offer::class.java)
                val good = Good(
                    offer.id,
                    offer.data.name,
                    offer.amount_breakdown.initial.toInt(),
                    offer.amount_breakdown.final.toInt(),
                    offer.amount_breakdown.discount.toInt()
                )
                goods.add(good)
            }
        } catch (e:Exception){
            e.printStackTrace()
        }

        return goods
    }
}