package com.example.poschtizettel.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PoschtiDatabaseDao{

    @Insert
    fun insertList(list: ShoppingList)

    @Insert
    fun insertListCommand(list: ListCommand)


    @Insert
    fun insertItem(item: ShoppingItems)

    @Insert
    fun insertItemCommand(item: ItemCommand)

    @Delete
    fun deleteList(list: ShoppingList)

    @Delete
    fun deleteItem(item: ShoppingItems)

    @Query("DELETE  FROM shopping_items WHERE item_key = :key")
    fun deleteItem(key: String)

    @Query("DELETE  FROM shopping_lists WHERE list_key = :key")
    fun deleteList(key: String)


    @Update
    fun updateList(list:ShoppingList)

    @Update
    fun updateItem(list:ShoppingList)

    @Query("SELECT * from shopping_lists WHERE List_Key = :key")
    fun getList(key: String): ShoppingList?

    @Query("SELECT * from shopping_items WHERE item_key = :key")
    fun getItem(key: String): ShoppingItems?

    @Query("SELECT * from shopping_items WHERE list_key = :key ORDER BY shop ASC")
    fun getListItems(key: String): List<ShoppingItems>


    @Query("SELECT * from shopping_lists")
    fun getAllLists(): List<ShoppingList>

    @Query("SELECT * from shopping_lists")
    fun getAllListsLive(): LiveData<List<ShoppingList>>

    @Query("SELECT * from shopping_items")
    fun getAllItems(): List<ShoppingItems>

    @Query("SELECT * from shopping_items WHERE list_key = :key ORDER BY shop ASC, priority desc")
    fun getAllItemsOfList(key: String): List<ShoppingItems>

    @Query("UPDATE shopping_items SET done = :status where item_key=:key")
    fun updateItemDoneStatus(key: String, status: Boolean)

    @Query("DELETE FROM shopping_lists")
    fun nukeLists()

    @Query("DELETE FROM shopping_items")
    fun nukeItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLists(vararg lists : ShoppingList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg items: ShoppingItems)

}