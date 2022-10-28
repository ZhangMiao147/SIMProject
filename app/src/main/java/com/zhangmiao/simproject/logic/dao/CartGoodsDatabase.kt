package com.zhangmiao.simproject.logic.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhangmiao.simproject.logic.model.CartGoods

@Database(version = 1, entities = [CartGoods::class])
abstract class CartGoodsDatabase : RoomDatabase() {

    abstract fun cartGoodsDao(): CartGoodsDao

    companion object {
        private var instance: CartGoodsDatabase? = null
        val TABLE_NAME = "cart_goods_database"

        @Synchronized
        fun getDatabase(context: Context): CartGoodsDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(
                context.applicationContext,
                CartGoodsDatabase::class.java,
                TABLE_NAME
            )
                .build().apply {
                    instance = this
                }
        }
    }
}