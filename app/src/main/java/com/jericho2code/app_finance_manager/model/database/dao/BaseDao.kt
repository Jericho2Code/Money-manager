package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<T>?)

    @Update
    fun update(item: T)
}