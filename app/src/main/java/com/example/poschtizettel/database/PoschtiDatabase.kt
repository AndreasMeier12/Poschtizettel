package com.example.poschtizettel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingList::class, ShoppingItems::class], version = 3, exportSchema = false)
abstract class PoschtiDatabase : RoomDatabase() {

    abstract val poschtiDatabaseDao: PoschtiDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PoschtiDatabase? = null

        fun getInstance(context: Context): PoschtiDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PoschtiDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}