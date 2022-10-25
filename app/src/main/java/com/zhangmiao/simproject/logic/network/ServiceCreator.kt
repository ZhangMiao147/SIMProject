package com.zhangmiao.simproject.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络服务创建器
 */
object ServiceCreator {

    private const val BASE_URL="https://api-dc94.lotusflare.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create():T = create(T::class.java)

}