package com.example.poschtizettel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.poschtizettel.database.ShoppingList


class ShoppingListAdapter(private var shoppingLists: MutableList<ShoppingList>, private val viewModel: ListsViewModel, private val parentFragment: ShoppingListsFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var selectListButton: Button
        var messageButton: Button
        init {
            selectListButton = itemView.findViewById(R.id.button_select_list)
            messageButton = itemView.findViewById(R.id.message_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val listView = inflater.inflate(R.layout.item_list, parent, false)
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
        var selectButton = holder.selectListButton
        val button: Button = holder.messageButton
        button.text = "Delete"
        selectButton.text = currentList.name
        button.setOnClickListener {
            removeAt(position)
            viewModel.deleteList(currentList.listKey)
        }

        selectButton.setOnClickListener {
            parentFragment.navigateToSingleList(currentList.listKey)
        }

    }

    fun removeAt(position: Int){
        shoppingLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, shoppingLists.size)

    }

}