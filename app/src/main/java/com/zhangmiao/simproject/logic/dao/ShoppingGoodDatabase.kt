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

        @Synchronized
        fun getDatabase(context: Context): ShoppingGoodDatabase {
            instance?.let {
                return it
            }

            return Room.databaseBuilder(
                context.applicationContext,
                ShoppingGoodDatabase::class.java,
                "shopping_good_database"
            )
                .build().apply {
                    instance = this
                }
        }
    }
}