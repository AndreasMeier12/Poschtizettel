package com.example.poschtizettel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poschtizettel.database.ShoppingList


class ShoppingListAdapter(private val shoppingLists: List<ShoppingList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameTextView: TextView
        var messageButton: Button

        init {
            nameTextView = itemView.findViewById(R.id.contact_name)
            messageButton = itemView.findViewById(R.id.message_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val listView = inflater.inflate(R.layout.item_shopping_list_layout, parent)
        val viewHolder = ViewHolder(itemView = listView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return shoppingLists.size
    }

    fun onBindViewHolder(holder: ShoppingListAdapter.ViewHolder, position: Int) {


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listViewHolder : ShoppingListAdapter.ViewHolder = holder as  ShoppingListAdapter.ViewHolder
        val currentList = shoppingLists.get(position)
        var textView = holder.nameTextView
        val button: Button = holder.messageButton
        button.text = "Delete"
    }

}