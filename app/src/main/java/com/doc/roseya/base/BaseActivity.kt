package com.doc.roseya.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.doc.roseya.room.databaseDao.DatabaseDAO
import com.doc.roseya.session.PrefManager
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {


    @Inject
    lateinit var db: DatabaseDAO
    lateinit var database: DatabaseDAO
    private var backPressedTime:Long = 0
    lateinit var backToast: Toast
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DatabaseDAO.getInstance(this)
        db = Room.databaseBuilder(applicationContext, DatabaseDAO::class.java, "rose.db")
            .allowMainThreadQueries().build()
    }
}
