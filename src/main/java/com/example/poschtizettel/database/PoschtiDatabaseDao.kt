package com.example.poschtizettel.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

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




}