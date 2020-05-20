package com.example.poschtizettel.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import org.jetbrains.annotations.NotNull

@Entity(tableName = "shopping_lists", indices = [Index(value = ["Name"])])
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="List_Key")
    val listKey: Int=0,
    @ColumnInfo(name = "Name")
    val name: String){}

@Entity(tableName = "Shopping_Items", foreignKeys = arrayOf(ForeignKey(entity = ShoppingList::class, parentColumns = arrayOf("List_Key"), childColumns = arrayOf("list_key"), onDelete = CASCADE)))
data class ShoppingItems(
    @PrimaryKey(autoGenerate = true)
    var item_key:Int = 0,

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
    var done: Boolean = false,

    @ColumnInfo(name = "shop")
    val shop : String = "",

    @ColumnInfo(name = "priority")
    val priority: Int = 0
)

