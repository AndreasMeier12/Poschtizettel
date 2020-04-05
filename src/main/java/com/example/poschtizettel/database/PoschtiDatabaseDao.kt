package com.example.poschtizettel.database

import androidx.room.*

@Dao
interface PoschtiDatabaseDao{

    @Insert
    fun insertList(list: ShoppingList)

    @Insert
    fun insertItem(item: ShoppingItems)

    @Delete
    fun deleteList(list: ShoppingList)

    @Delete
    fun deleteItem(item: ShoppingItems)

    @Query("DELETE  FROM shopping_items WHERE item_key = :key")
    fun deleteItem(key: Int)

    @Update
    fun updateList(list:ShoppingList)

    @Update
    fun updateItem(list:ShoppingList)

    @Query("SELECT * from shopping_lists WHERE List_Key = :key")
    fun getList(key: Int): ShoppingList?

    @Query("SELECT * from shopping_items WHERE item_key = :key")
    fun getItem(key: Int): ShoppingItems?

    @Query("SELECT * from shopping_items WHERE list_key = :key")
    fun getListItems(key: Int): List<ShoppingItems>?

    @Query("SELECT * from shopping_lists")
    fun getAllLists(): List<ShoppingList>?

    @Query("SELECT * from shopping_items")
    fun getAllItems(): List<ShoppingItems>?

    @Query("SELECT * from shopping_items WHERE list_key = :key")
    fun getAllItemsOfList(key: Int): List<ShoppingItems>?


}