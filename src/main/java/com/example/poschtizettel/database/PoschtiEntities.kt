package com.example.poschtizettel.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_lists")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="List_Key")
    val listKey: Int=0,
    @ColumnInfo(name = "Name")
    val name: String){}

@Entity(tableName = "Shopping_Items")
data class ShoppingItems(
    @PrimaryKey(autoGenerate = true)
    var item_key:Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "quantity")
    val quantity :String,

    @ColumnInfo(name = "unit")
    val unit:String = "",

    @ColumnInfo(name="list_key")
    val shoppingList: Int,

    @ColumnInfo(name= "done")
    val done: Boolean = false
)

@Entity(tableName = "Shop")
data class Shop(
    @PrimaryKey(autoGenerate = true)
    var shopKey:Int,

    @ColumnInfo(name = "Name")
    var name: String,

    @ColumnInfo(name="Priority")
    var priority: Int
    )