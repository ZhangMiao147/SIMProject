package com.zhangmiao.simproject.logic.network

import android.util.Log
import com.google.gson.Gson
import com.zhangmiao.simproject.SIMApplication
import com.zhangmiao.simproject.logic.model.GetOffersResponse
import com.zhangmiao.simproject.logic.model.OffersRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object OffersNetwork {

    private val TAG = OffersNetwork::class.simpleName

    private val offersService = ServiceCreator.create(OffersService::class.java)

    suspend fun getOffers(request: OffersRequest) =
        offersService.getOffers(request).await()

    private suspend fun Call<GetOffersResponse>.await(): GetOffersResponse {
        return suspendCoroutine { continuation ->
            Log.d(TAG,"getOffers await")
            enqueue(object : Callback<GetOffersResponse> {
                override fun onResponse(call: Call<GetOffersResponse>, response: Response<GetOffersResponse>) {
                    val body:GetOffersResponse? = response.body()
                    Log.d(TAG,"getOffers onResponse response:${response}")
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        java.lang.RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<GetOffersResponse>, t: Throwable) {
                    Log.d(TAG,"getOffers onFailure t:${t}")
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}