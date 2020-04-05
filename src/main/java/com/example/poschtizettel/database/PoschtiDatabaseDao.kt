package com.example.poschtizettel.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.poschtizettel.ShoppingItem

@Dao
interface PoschtiDatabaseDao{

    @Insert
    fun insertList(list: ShoppingList)

    @Insert
    fun insertItem(item: ShoppingItems)

    @Update
    fun updateList(list:ShoppingList)

    @Update
    fun updateItem(list:ShoppingList)

    @Query("SELECT * from shopping_lists WHERE List_Key = :key")
    fun getList(key: Int): ShoppingList?

    @Query("SELECT * from shopping_items WHERE item_key = :key")
    fun getItem(key: Int): ShoppingItem?

    @Query("SELECT * from shopping_items WHERE list_key = :key")
    fun getListItems(key: Int): List<ShoppingItem>?



}