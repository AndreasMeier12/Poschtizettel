package com.example.poschtizettel

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.poschtizettel.database.PoschtiDatabase
import com.example.poschtizettel.database.PoschtiDatabaseDao
import com.example.poschtizettel.database.ShoppingItems
import com.example.poschtizettel.database.ShoppingList
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dbDAO: PoschtiDatabaseDao
    private lateinit var db: PoschtiDatabase

    @Before
    fun createDb() {
        Log.d("Test", "Database instantiated")

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PoschtiDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dbDAO = db.poschtiDatabaseDao
        Log.d("Test", "Database instantiated")

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetList(){
        val listName = "testlist"
        val myList = ShoppingList(name = listName)
        val key = myList.listKey+1
        Log.i("TestDB", "key inserted ${key}")
        dbDAO.insertList(myList)
        val retrieved = dbDAO.getList(key)
        assertNotNull(retrieved)
        assertEquals(retrieved?.name, listName)
    }


    @Test(expected = SQLiteConstraintException::class)
    fun testCannotNameMultipleTimes(){
        val listName = "testlist"
        for (i in 1..15){
            dbDAO.insertList(ShoppingList(name = listName))
        }
        val dbLists = dbDAO.getAllLists()
        assertEquals(dbLists?.size, 1)

    }

    @Test(expected = SQLiteConstraintException::class)
    fun testInsertMultipleLists(){
        val listName = "testlist"
        for (i in 1..15){
            dbDAO.insertList(ShoppingList(name = "%{listName}%{i}"))
        }
        val dbLists = dbDAO.getAllLists()
        assertEquals(dbLists?.size, 15)

    }

    @Test
    fun addItemsToList(){
        val listName = "testlist"
        val myList = ShoppingList(name = listName)
        val listKey = myList.listKey+1
        dbDAO.insertList(myList)
        dbDAO.insertItem(ShoppingItems(name = "lentils", shoppingList = listKey, quantity = "1"))
        dbDAO.insertItem(ShoppingItems(name = "rum", shoppingList = listKey, quantity = "1"))
        dbDAO.insertItem(ShoppingItems(name = "electrons", shoppingList = listKey, quantity = "6e23"))
        dbDAO.insertItem(ShoppingItems(name = "barley", shoppingList = listKey, quantity = "1"))
        val items = dbDAO.getAllItemsOfList(listKey)
        assertEquals(items?.size, 4)
    }

    @Test
    fun deleteList(){
        val listName = "testlist"
        val myList = ShoppingList(name = listName)
        val listNum = dbDAO.insertList(myList)
        val lists = dbDAO.getAllLists()
        var listKey = 0
        if(lists != null){listKey = lists.get(0).listKey}
        val lentils = ShoppingItems(name = "lentils", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(lentils)
        val rum = ShoppingItems(name = "rum", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(rum)
        val electrons = ShoppingItems(name = "electrons", shoppingList = listKey, quantity = "6e23")
        dbDAO.insertItem(electrons)
        val barley = ShoppingItems (name = "barley", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(barley)
        dbDAO.deleteList(listKey)
        val newLists = dbDAO.getAllLists()
        val newItems = dbDAO.getAllItems()
        assertEquals(newLists?.size, 0)
        assertEquals(newItems?.size, 0)
    }

    @Test
    fun deleteOneListOfMultiple(){
        val listName = "testlist"
        val listName2 = "differentList"
        val listName3 = "evenmoredifferentlist"

        val myList = ShoppingList(name = listName)
        val listNum = dbDAO.insertList(myList)
        val lists = dbDAO.getAllLists()
        var listKey = 0
        if(lists != null){listKey = lists.get(0).listKey}
        dbDAO.insertList(ShoppingList(name = listName2))
        dbDAO.insertList(ShoppingList(name = listName3))

        val lentils = ShoppingItems(name = "lentils", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(lentils)
        val rum = ShoppingItems(name = "rum", shoppingList = 2, quantity = "1")
        dbDAO.insertItem(rum)
        val electrons = ShoppingItems(name = "electrons", shoppingList = 2, quantity = "6e23")
        dbDAO.insertItem(electrons)
        val barley = ShoppingItems (name = "barley", shoppingList = 3, quantity = "1")
        dbDAO.insertItem(barley)
        dbDAO.deleteList(listKey)
        val newLists = dbDAO.getAllLists()
        val liveLists = dbDAO.getAllListsLive().value
        val newItems = dbDAO.getAllItems()
        assertEquals(newLists?.size, 2)
        assertEquals(newItems?.size, 3)
    }


    @Test
    fun deleteItem(){
        val listName = "testlist"
        val myList = ShoppingList(name = listName)
        val listNum = dbDAO.insertList(myList)

        val lists = dbDAO.getAllLists()
        var listKey = 0
        if(lists != null){listKey = lists.get(0).listKey}
        val lentils = ShoppingItems(name = "lentils", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(lentils)
        val rum = ShoppingItems(name = "rum", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(rum)
        val electrons = ShoppingItems(name = "electrons", shoppingList = listKey, quantity = "6e23")
        dbDAO.insertItem(electrons)
        val barley = ShoppingItems (name = "barley", shoppingList = listKey, quantity = "1")
        dbDAO.insertItem(barley)

        val asdf = dbDAO.getItem(1)

        if (asdf != null) {
            dbDAO.deleteItem(asdf)
        }
        val items = dbDAO.getAllItemsOfList(listKey)
        assertEquals(3, items?.size)
    }

}
