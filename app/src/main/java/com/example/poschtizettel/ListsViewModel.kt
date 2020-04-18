package com.example.poschtizettel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.poschtizettel.database.PoschtiDatabaseDao
import com.example.poschtizettel.database.ShoppingItems
import com.example.poschtizettel.database.ShoppingList
import kotlinx.coroutines.*
import java.util.function.BinaryOperator

class ListsViewModel(val databaseDao: PoschtiDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val lists = getLiveLists()
    val listsString = Transformations.map(lists) { lists ->
        formatShoppingLists(lists)
    }

    init {
        Log.i("ListViewModel", "init")
    }

    fun onAddList(name: String) {
        uiScope.launch {
            insertList(name)
            Log.i("ListsViewModel", "List $name added")

        }

    }


    suspend fun insertList(name: String) {
        withContext(Dispatchers.IO) {
            databaseDao.insertList(ShoppingList(name = name))
            val asdf = databaseDao.getAllLists()
            val fsda = databaseDao.getList(0)
            Log.i("Coroutine", fsda.toString())
        }
    }

    fun onAddItem(listNum: Int, name: String, quantity: String = "", unit: String="", shop: String ="") {
        uiScope.launch {
            insertItem(listNum, name, unit = unit, quantity = quantity, shop = shop)
            Log.i("ListsViewModel", "Item $name added to list $listNum")

        }
    }

    fun onGetLists() {}

    suspend fun getLists() {

    }

    suspend fun insertItem(listNum: Int, name: String, quantity: String="", unit: String = "", shop:String ="") {
        withContext(Dispatchers.IO) {
            databaseDao.insertItem(
                ShoppingItems(
                    name = name,
                    shoppingList = listNum,
                    quantity = quantity,
                    unit = unit,
                    shop = shop
                )
            )
        }
    }

    fun getLiveLists(): LiveData<List<ShoppingList>> {
        val asdf = databaseDao.getAllListsLive()
        val fsda = asdf.value
        Log.i("ListViewModel", "lists: $fsda")
        return asdf

    }

    fun getDasLists(): List<ShoppingList> {
        var res: List<ShoppingList> = listOf()
        runBlocking {
            res = async { getDasListsForRealsies() }.await()
        }
        return res


    }


    suspend fun getDasListsForRealsies() = withContext(Dispatchers.IO) {
        databaseDao.getAllLists()
    }

    fun deleteList(key: Int){
        runBlocking {
            deleteListCoroutine(key)
        }
    }

    private suspend fun deleteListCoroutine(key: Int) = withContext(Dispatchers.IO){
        databaseDao.deleteList(key)
    }

    fun deleteItem(key: Int){
        runBlocking {
            deleteItemCoroutine(key)
        }
    }

    private suspend fun deleteItemCoroutine(key: Int) = withContext(Dispatchers.IO){
        databaseDao.deleteItem(key)
    }

    fun getItemsOfList(key: Int) : List<ShoppingItems>{
        var res: List<ShoppingItems> = listOf()
        runBlocking {
            res = async { getItemsOfListCoroutine(key) }.await()
        }
        return res


    }

    suspend private fun getItemsOfListCoroutine(key: Int) = withContext(Dispatchers.IO){
        databaseDao.getListItems(key)

    }

    fun handleItemDone(key: Int, status: Boolean){
        runBlocking {
            handleItemDoneCoRoutine(key, status)

        }

    }

    suspend private fun handleItemDoneCoRoutine(key: Int, status: Boolean){
        withContext(Dispatchers.IO){
            databaseDao.updateItemDoneStatus(key, status)

        }

    }




}