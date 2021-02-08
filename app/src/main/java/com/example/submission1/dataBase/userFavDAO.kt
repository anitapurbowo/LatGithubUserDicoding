package com.example.submission1.dataBase

import androidx.room.*
import com.example.submission1.dataBase.dataBaseFunc.userColumns.Companion.Nama
import com.example.submission1.dataBase.dataBaseFunc.userColumns.Companion.TABLE_NAME
import com.example.submission1.dataclass.userFav

@Dao
interface userFavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahUser(user: userFav)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $Nama ASC")
    suspend fun getAllUser() : List<userFav>

    @Delete
    suspend fun deleteUser(user : userFav)

    @Query("SELECT * FROM $TABLE_NAME where $Nama=:nama")
    suspend fun getUser(nama : String) : userFav
}