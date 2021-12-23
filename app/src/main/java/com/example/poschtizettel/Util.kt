package com.example.poschtizettel

import android.text.Html
import android.text.Spanned
import com.example.poschtizettel.database.ItemCommand
import com.example.poschtizettel.database.ListCommand
import com.example.poschtizettel.database.ShoppingItems
import com.example.poschtizettel.database.ShoppingList


fun formatShoppingList(list: ShoppingList){

}

fun formatShoppingLists(lists: List<ShoppingList>) : Spanned{
    val sb = StringBuilder()
    sb.apply { append("<h3>Shopping Lists</h3>")
        lists.forEach{
            append("<p>${it.name}")
        }}

    return Html.fromHtml(sb.toString())


}

fun formatShoppingItem(item: ShoppingItems){

}

fun formatShoppingItem(items: List<ShoppingItems>){

}

