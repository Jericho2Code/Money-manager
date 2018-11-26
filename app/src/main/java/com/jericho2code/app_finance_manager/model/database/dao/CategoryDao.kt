package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.CATEGORY_TABLE
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.ID
import io.reactivex.Single

@Dao
interface CategoryDao : BaseDao<Category> {
    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun categories(): LiveData<List<Category>>

    @Query("SELECT * FROM $CATEGORY_TABLE WHERE $ID = :id")
    fun categoryById(id: Long): LiveData<Category>
}