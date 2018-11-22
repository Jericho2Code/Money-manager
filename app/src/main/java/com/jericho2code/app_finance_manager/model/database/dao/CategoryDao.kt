package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.CATEGORY_TABLE
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.ID
import io.reactivex.Single

interface CategoryDao : BaseDao<Category> {
    @Query("SELECT * FROM $CATEGORY_TABLE")
    fun transaction(): Single<List<Category>>

    @Query("SELECT * FROM $CATEGORY_TABLE WHERE $ID = :id")
    fun transactionById(id: Long): Single<Category>
}