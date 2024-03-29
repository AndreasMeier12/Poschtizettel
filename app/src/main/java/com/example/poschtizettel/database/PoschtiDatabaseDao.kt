package com.example.poschtizettel.database

import androidx.lifecycle.LiveData
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

    @Query("DELETE  FROM shopping_lists WHERE list_key = :key")
    fun deleteList(key: Int)


    @Update
    fun updateList(list:ShoppingList)

    @Update
    fun updateItem(list:ShoppingList)

    @Query("SELECT * from shopping_lists WHERE List_Key = :key")
    fun getList(key: Int): ShoppingList?

    @Query("SELECT * from shopping_items WHERE item_key = :key")
    fun getItem(key: Int): ShoppingItems?

    @Query("SELECT * from shopping_items WHERE list_key = :key ORDER BY shop ASC")
    fun getListItems(key: Int): List<ShoppingItems>


    @Query("SELECT * from shopping_lists")
    fun getAllLists(): List<ShoppingList>

    @Query("SELECT * from shopping_lists")
    fun getAllListsLive(): LiveData<List<ShoppingList>>

    @Query("SELECT * from shopping_items")
    fun getAllItems(): List<ShoppingItems>

    @Query("SELECT * from shopping_items WHERE list_key = :key ORDER BY shop ASC, priority desc")
    fun getAllItemsOfList(key: Int): List<ShoppingItems>

    @Query("UPDATE shopping_items SET done = :status where item_key=:key")
    fun updateItemDoneStatus(key: Int, status: Boolean)



}