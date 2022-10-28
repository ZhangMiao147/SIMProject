package com.zhangmiao.simproject.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartGoods(

    @PrimaryKey
    val id: String,

    val name: String,

    val amount: Int,

    var num: Int,

    var select: Boolean

) {

    override fun equals(other: Any?): Boolean {
        return if (other is CartGoods) {
            other.id == id && other.name == name && other.amount == amount
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode() + name.hashCode() + amount.hashCode()
    }
}
