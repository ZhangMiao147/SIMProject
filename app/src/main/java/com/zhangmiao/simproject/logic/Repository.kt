package com.zhangmiao.simproject.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.zhangmiao.simproject.logic.model.GetOffersResponse
import com.zhangmiao.simproject.logic.model.Good
import com.zhangmiao.simproject.logic.network.OffersNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

object Repository {

    val TAG = "Repository"

    fun getOffers(type: String, operator_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val offersResponse = OffersNetwork.getOffers(type, operator_name)
            Log.d(TAG,"getOffers offersResponse:${offersResponse}")
            val goods:List<Good> = switchToGoods(offersResponse)
            Log.d(TAG,"getOffers goods:${goods}")
            Result.success(goods)
        } catch (e: Exception) {
            Result.failure<List<Good>>(e)
        }
        emit(result)
    }

    /**
     * 将网络请求数据转换为展示数据
     */
    fun switchToGoods(getOffersResponse: GetOffersResponse):ArrayList<Good>{
        var goods: ArrayList<Good> = ArrayList<Good>()
        Log.d(TAG,"switchToGoods goods:${goods}")
        return goods
    }
}