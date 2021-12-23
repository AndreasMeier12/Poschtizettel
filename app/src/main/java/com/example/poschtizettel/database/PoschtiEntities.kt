package com.example.poschtizettel.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.poschtizettel.CommandType
import org.jetbrains.annotations.NotNull
import kotlinx.serialization.Serializable
import java.util.*

@Entity(tableName = "shopping_lists", indices = [Index(value = ["Name"])])
data class ShoppingList(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="List_Key")
    val listKey: String="-1",
    @ColumnInfo(name = "Name")
    val name: String){}

@Entity(tableName = "Shopping_Items", foreignKeys = arrayOf(ForeignKey(entity = ShoppingList::class, parentColumns = arrayOf("List_Key"), childColumns = arrayOf("list_key"), onDelete = CASCADE)))
data class ShoppingItems(
    @PrimaryKey(autoGenerate = false)
    var item_key:String = "-1",

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "quantity")
    val quantity :String,

    @ColumnInfo(name = "unit")
    val unit:String = "",

    @ColumnInfo(name="list_key")
    @NotNull
    val shoppingList: String = "0",

    @ColumnInfo(name= "done")
    var done: Boolean = false,

    @ColumnInfo(name = "shop")
    val shop : String = "",

    @ColumnInfo(name = "priority")
    val priority: Int = 0
)

@Entity(tableName = "item_commands", foreignKeys = arrayOf(ForeignKey(entity = ShoppingList::class, parentColumns = arrayOf("List_Key"), childColumns = arrayOf("list_key"), onDelete = CASCADE)))
data class ItemCommand(
    @PrimaryKey(autoGenerate = false)
    var commandKey:String = "UUID.randomUUID().toString()",

    @ColumnInfo(name="itemKey")
    var itemKey:String = "",

    @ColumnInfo(name = "name")
    val name :String,


    @ColumnInfo(name = "quantity")
    val quantity :String,

    @ColumnInfo(name = "unit")
    val unit:String = "",

    @ColumnInfo(name="list_key")
    @NotNull
    val shoppingList: String = "",

    @ColumnInfo(name= "done")
    var done: Boolean = false,

    @ColumnInfo(name = "shop")
    val shop : String = "",

    @ColumnInfo(name = "type")
    val type: CommandType,

    @ColumnInfo(name="timestamp")
    val timestamp: Long = System.currentTimeMillis()

)

@Serializable
@Entity(tableName = "list_commands", indices = [Index(value = ["Name"])])
data class ListCommand(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="List_Key")
    val listKey: String="",
    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name="Commandtype")
    val type: CommandType,
    @ColumnInfo(name = "Timestamp")
    val timestamp: Long = System.currentTimeMillis()
    ){

}



class Converters {
    @TypeConverter
    fun fromCommandType(value: CommandType): Int {
        return value.id;
    }

    @TypeConverter
    fun toCommandType(value: Int): CommandType? {
        return CommandType.values().filter { it.id == value  }.firstOrNull();
    }



}


