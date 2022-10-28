package com.zhangmiao.simproject.logic.model

sealed class GoodsListResponse {
    data class Response(val goodsList: List<Goods>) : GoodsListResponse()

    object Failure : GoodsListResponse()
}