package com.example.poschtizettel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poschtizettel.database.PoschtiDatabaseDao
import com.example.poschtizettel.database.ShoppingList
import kotlinx.coroutines.*

class ListsViewModel(val databaseDao: PoschtiDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


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
        withContext(Dispatchers.IO){
            databaseDao.insertList(ShoppingList(name = name))
        }
    }


}