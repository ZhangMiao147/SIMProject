package com.zhangmiao.simproject.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zhangmiao.simproject.logic.model.CartGoods

@Dao
interface CartGoodsDao {

    @Insert
    fun insertCartGoods(cartGoods: CartGoods): Long

    @Update
    fun updateCartGoods(cartGoods: CartGoods)

    @Query("select * from CartGoods")
    suspend fun getCartGoodsList(): List<CartGoods>

    @Delete
    fun deleteCartGoods(cartGoods: CartGoods)

    @Query("delete from CartGoods where `select`=:select")
    fun deleteCartGoods(select: Boolean = true)

    @Query("delete from CartGoods")
    fun deleteAllCartGoods()

}