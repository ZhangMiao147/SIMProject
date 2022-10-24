package com.zhangmiao.simproject.logic.network

import com.zhangmiao.simproject.logic.model.GetOffersResponse
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * 商品接口定义
 */
interface OffersService {

    /**
     * 获取商品列表
     */
    @Multipart
    @POST("api/v3/catalog/get_offers")
    fun getOffers(
        @Part("type") type: String,
        @Part("operator_name") operator_name: String
    ): Call<GetOffersResponse>
}