package com.zhangmiao.simproject.logic.model

data class ShoppingGood(
    /**
     * 商品id
     */
     val id: String,
    /**
     * 商品名称
     */
     val name: String,

    /**
     * 商品金额
     */
    val amount: Int,

    /**
     * 商品数量
     */
    var num: Int,

    /**
     * 商品是否被选中
     */
    var select:Boolean

    ) {

    override fun equals(other: Any?): Boolean {
        return if(other is Good){
            other.id.equals(id)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()+name.hashCode()+amount.hashCode()
    }
}
