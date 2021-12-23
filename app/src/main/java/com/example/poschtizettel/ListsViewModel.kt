package com.example.poschtizettel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.poschtizettel.database.*
import kotlinx.coroutines.*
import java.util.*

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
            val listKey = UUID.randomUUID().toString()
            databaseDao.insertList(ShoppingList(listKey,name = name))
            databaseDao.insertListCommand(ListCommand(listKey = listKey, name=name, type=CommandType.CREATE))
        }
    }

    fun onAddItem(listNum: String, name: String, quantity: String = "", shop: String ="") {
        uiScope.launch {
            insertItem(listNum, name, quantity = quantity, shop = shop)
            Log.i("ListsViewModel", "Item $name added to list $listNum")

        }
    }

    fun onGetLists() {}

    suspend fun getLists() {

    }

    suspend fun insertItem(listNum: String, name: String, quantity: String="", shop:String ="") {
        withContext(Dispatchers.IO) {
            val itemKey = UUID.randomUUID().toString()
            databaseDao.insertItem(
                ShoppingItems(
                    name = name,
                    shoppingList = listNum,
                    quantity = quantity,
                    shop = shop
                )
            )
            databaseDao.insertItemCommand(ItemCommand(itemKey = itemKey, name = name, quantity = quantity, unit = "", shoppingList = listNum, done=false, shop = shop, type = CommandType.CREATE))

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

    fun deleteList(key: String){
        runBlocking {
            deleteListCoroutine(key)
        }
    }

    private suspend fun deleteListCoroutine(key: String) = withContext(Dispatchers.IO){
        var asdf = databaseDao.getList(key)
        if (asdf == null){
            asdf = ShoppingList(key, "")
        }
        databaseDao.deleteList(key)
        databaseDao.insertListCommand(ListCommand(key, asdf.name, CommandType.DELETE))
    }

    fun getAllItems(): List<ShoppingItems> {
        var res: List<ShoppingItems> = listOf()
        runBlocking {
            res = async { getAllItemsForRealsies() }.await()
        }
        return res


    }

    suspend fun getAllItemsForRealsies() = withContext(Dispatchers.IO) {
        databaseDao.getAllItems()
    }

    fun deleteItem(key: String){
        runBlocking {
            deleteItemCoroutine(key)
        }
    }

    private suspend fun deleteItemCoroutine(key: String) = withContext(Dispatchers.IO){
        var asdf = databaseDao.getItem(key)
        if (asdf == null){
           asdf = ShoppingItems(key, "", "", "", "", false, "")
        }
        databaseDao.deleteItem(key)
        databaseDao.insertItemCommand(ItemCommand(key, asdf.name, asdf.quantity, asdf.unit, asdf.shoppingList, false, asdf.shop, CommandType.DELETE ))
    }

    fun getItemsOfList(key: String) : List<ShoppingItems>{
        var res: List<ShoppingItems> = listOf()
        runBlocking {
            res = async { getItemsOfListCoroutine(key) }.await()
        }
        return res


    }

    suspend private fun getItemsOfListCoroutine(key: String) = withContext(Dispatchers.IO){
        databaseDao.getListItems(key)

    }

    fun handleItemDone(key: String, status: Boolean){
        runBlocking {
            handleItemDoneCoRoutine(key, status)

        }

    }

    suspend private fun handleItemDoneCoRoutine(key: String, status: Boolean){
        withContext(Dispatchers.IO){
            var asdf = databaseDao.getItem(key)
            val newId = UUID.randomUUID().toString()
            if (asdf == null){
                asdf = ShoppingItems(key, "", "", "", "", false, "")
            }
            databaseDao.updateItemDoneStatus(key, status)
            databaseDao.insertItemCommand(ItemCommand(newId, asdf.name, asdf.quantity, asdf.unit, asdf.shoppingList, !asdf.done, asdf.shop, CommandType.UPDATE ))

        }

    }

    fun setContent(lists: List<ShoppingList>, items: List<ShoppingItems>){
        runBlocking {
            setContentCoroutine(lists, items)
        }


    }

    suspend private fun setContentCoroutine(lists: List<ShoppingList>, items: List<ShoppingItems>){
        withContext(Dispatchers.IO){
            databaseDao.nukeItems()
            databaseDao.nukeLists()
            databaseDao.insertLists(*lists.toTypedArray())
            databaseDao.insertItems(*items.toTypedArray())


        }


    }



}