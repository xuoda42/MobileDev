package com.example.list.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.list.data.Orders
import com.example.list.data.Company
import com.example.list.data.TourGuides
import com.example.list.data.User


@Database(
    entities = [Company::class,
        TourGuides::class,
        Orders::class,
                User::class
               ],
    version = 8,
    exportSchema = false
)
@TypeConverters(ListTypeConverters::class)

abstract class ListDatabase: RoomDatabase() {
    abstract fun listDAO(): ListDAO

    companion object {
        @Volatile
        private var INSTANCE: ListDatabase? = null

        fun getDatabase(context: Context): ListDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ListDatabase::class.java,
            "Сompany_database")
            .fallbackToDestructiveMigration()
            .build()

    }
}