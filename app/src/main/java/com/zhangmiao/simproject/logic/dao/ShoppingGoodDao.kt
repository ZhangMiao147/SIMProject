package com.zhangmiao.simproject.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zhangmiao.simproject.logic.model.ShoppingGood

@Dao
interface ShoppingGoodDao {

 @Insert
 fun insertShoppingGood(shoppingGood: ShoppingGood):Long

 @Update
 fun updateShoppingGood(newShoppingGood: ShoppingGood)

 @Query("select * from ShoppingGood")
 fun getShoppingGoodList():LiveData<List<ShoppingGood>>

 @Delete
 fun deleteShoppingGood(shoppingGood: ShoppingGood)

 @Query("delete from ShoppingGood")
 fun deleteAllShoppingGood()

}