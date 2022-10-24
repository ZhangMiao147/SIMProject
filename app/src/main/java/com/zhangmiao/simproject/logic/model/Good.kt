package com.zhangmiao.simproject.logic.model

data class Good(
    /**
     * 商品id
     */
    val id: String,
    /**
     * 商品名称
     */
    val name: String,

    /**
     * 商品初始金额
     */
    val amount_initial: Int,

    /**
     * 商品最终金额
     */
    val amount_final: Int,

    /**
     * 折扣
     */
    val discount: Int,

    )
