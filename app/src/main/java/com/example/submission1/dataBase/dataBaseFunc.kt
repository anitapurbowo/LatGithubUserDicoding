package com.example.submission1.dataBase

import android.provider.BaseColumns

internal class dataBaseFunc {
    internal class userColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "userfav"
            const val Nama = "dbNama"
        }
    }
}

