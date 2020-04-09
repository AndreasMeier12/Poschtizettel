package com.example.poschtizettel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.poschtizettel.database.PoschtiDatabaseDao
import com.example.poschtizettel.database.ShoppingItems
import com.example.poschtizettel.database.ShoppingList
import kotlinx.coroutines.*

class ListsViewModel(val databaseDao: PoschtiDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val lists = getLiveLists()
    val listsString = Transformations.map(lists) { lists ->
        formatShoppingLists(lists)
    }
    init {
        Log.i("ListViewModel", "init")
    }

    fun onAddList(name: String) {
        uiScope.launch {
            insertList(name)
            Log.i("ListsViewModel","List $name added")

        }

    }


    suspend fun insertList(name: String){
        withContext(Dispatchers.IO) {
            databaseDao.insertList(ShoppingList(name = name))
        }
    }

    fun onAddItem(listNum: Int, name: String){
        uiScope.launch {
            insertItem(listNum, name)
            Log.i("ListsViewModel","Item $name added to list $listNum")

        }
    }

    suspend fun insertItem(listNum: Int, name: String){
        withContext(Dispatchers.IO){
            databaseDao.insertItem(ShoppingItems(name = name, shoppingList = listNum, quantity = "", unit = ""))
        }
    }

    fun getLiveLists() : LiveData<List<ShoppingList>> {
        val asdf = databaseDao.getAllListsLive()
        return asdf

        }

}