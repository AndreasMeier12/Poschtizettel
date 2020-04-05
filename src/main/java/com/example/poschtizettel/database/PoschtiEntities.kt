package com.example.poschtizettel.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import org.jetbrains.annotations.NotNull

@Entity(tableName = "shopping_lists", indices = [Index(value = ["Name"], unique = true)])
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="List_Key")
    val listKey: Int=1,
    @ColumnInfo(name = "Name")
    val name: String){}

@Entity(tableName = "Shopping_Items", foreignKeys = arrayOf(ForeignKey(entity = ShoppingList::class, parentColumns = arrayOf("List_Key"), childColumns = arrayOf("list_key"), onDelete = CASCADE)))
data class ShoppingItems(
    @PrimaryKey(autoGenerate = true)
    var item_key:Int = 1,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "quantity")
    val quantity :String,

    @ColumnInfo(name = "unit")
    val unit:String = "",

    @ColumnInfo(name="list_key")
    @NotNull
    val shoppingList: Int = 0,

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
