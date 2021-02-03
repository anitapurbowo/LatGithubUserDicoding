package com.example.submission1.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1.userFav

@Database(entities = arrayOf(userFav::class), version = 1)

abstract class dataBaseHelper : RoomDatabase() {
    abstract fun UserFunDAO() : userFavDAO
    companion object {
        private var INSTANCE : dataBaseHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = dataBaseHelper
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            userFav::class.java,"dbUserFav.db"
        ).build()
    }
}