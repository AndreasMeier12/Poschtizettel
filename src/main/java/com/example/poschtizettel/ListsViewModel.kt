package com.example.poschtizettel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {
    private val _shoppingLists: MutableLiveData<MutableList<ShoppingList>> = MutableLiveData()
    get() {return field
    }

    init {
        Log.i("ListViewModel", "init")
    }




    fun addList(newList: ShoppingList){
        _shoppingLists.value?.add(newList)
        Log.i("ListViewModel", "List created")
    }

    fun removeList(deleteList: ShoppingList){
        _shoppingLists.value?.remove(deleteList)

    }





}