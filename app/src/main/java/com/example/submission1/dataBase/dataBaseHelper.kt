package com.example.submission1.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1.dataclass.userFav

@Database(entities = arrayOf(userFav::class), version = 1)

abstract class dataBaseHelper : RoomDatabase() {
    abstract fun UserFavDAO() : userFavDAO
    companion object {
        private var INSTANCE : dataBaseHelper? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): dataBaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    dataBaseHelper::class.java, "dbUGit.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }


}