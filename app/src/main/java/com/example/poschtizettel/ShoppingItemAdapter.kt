package com.example.poschtizettel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poschtizettel.database.ShoppingItems

class ShoppingItemAdapter(private val viewModel: ListsViewModel, val items: MutableList<ShoppingItems>, parent : SingleListFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameTextView: TextView
        var messageButton: Button
        init {
            nameTextView = itemView.findViewById(R.id.item_name)
            messageButton = itemView.findViewById(R.id.item_delete_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shoppingItemView = inflater.inflate(R.layout.item_shopping_item, parent, false)
        return ViewHolder(shoppingItemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder : ShoppingItemAdapter.ViewHolder = holder as ViewHolder
        val currentItem = items.get(position)
        val textView = holder.nameTextView
        val button = holder.messageButton
        button.text = "Delete"
        textView.text = currentItem.name



    }

    fun removeAt(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)

    }

}