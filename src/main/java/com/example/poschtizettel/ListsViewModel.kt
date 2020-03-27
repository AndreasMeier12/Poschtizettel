package com.example.poschtizettel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {
    private val _shoppingLists: MutableLiveData<MutableList<ShoppingList>> = MutableLiveData()
    get() {return field
    }




    fun addList(newList: ShoppingList){
        _shoppingLists.value?.add(newList)
    }





}