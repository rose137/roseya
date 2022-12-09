package com.doc.roseya.room.databaseDao

import android.content.Context
import androidx.room.*
import com.doc.roseya.model.LoginModel
import com.doc.roseya.room.dao.LoginDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Database(
    entities = arrayOf(
        LoginModel::class
    ),
    version = 1
)

@TypeConverters()
abstract class DatabaseDAO : RoomDatabase() {
    abstract fun LoginDao(): LoginDao


    companion object {
        private var INSTANCE: DatabaseDAO? = null

        fun getInstance(context: Context): DatabaseDAO {
            if (INSTANCE == null) {
                synchronized(DatabaseDAO::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseDAO::class.java,
                        "rose"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
