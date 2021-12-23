package com.example.poschtizettel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ShoppingList::class, ShoppingItems::class, ListCommand::class, ItemCommand::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
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
                        "poschtizettel_database"
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