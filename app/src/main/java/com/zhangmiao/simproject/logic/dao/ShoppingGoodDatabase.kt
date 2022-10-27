package com.zhangmiao.simproject.logic.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhangmiao.simproject.logic.model.ShoppingGood

@Database(version = 1, entities = [ShoppingGood::class])
abstract class ShoppingGoodDatabase : RoomDatabase() {



    abstract fun shoppingGoodDao(): ShoppingGoodDao


    companion object {
        private var instance: ShoppingGoodDatabase? = null
        val TABLE_NAME = "shopping_good_database"

        @Synchronized
        fun getDatabase(context: Context): ShoppingGoodDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(
                context.applicationContext,
                ShoppingGoodDatabase::class.java,
                TABLE_NAME
            )
                .build().apply {
                    instance = this
                }
        }
    }
}