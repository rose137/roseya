package com.doc.roseya.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.doc.roseya.model.LoginModel
import com.doc.roseya.base.BaseDao


@Dao
interface LoginDao : BaseDao<LoginModel> {
    @Query("SELECT * FROM login where email == :email")
    fun getSingle(email: String): LoginModel

    @Query("SELECT * FROM login")
    fun getAll(): List<LoginModel>


    @Query("SELECT *  FROM login where email == :email")
    fun getUserName(email: String): LoginModel

    @Query("DELETE FROM login")
    fun deleteAll()
}