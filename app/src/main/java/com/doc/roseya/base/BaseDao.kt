package com.doc.roseya.base

import androidx.room.*

interface  BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T?)

    @Update
    fun update(vararg items: T?)

    @Delete
    fun delete(item: T?)


}