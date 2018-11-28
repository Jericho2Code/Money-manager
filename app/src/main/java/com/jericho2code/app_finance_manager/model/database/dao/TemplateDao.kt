package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Template
import com.jericho2code.app_finance_manager.model.entity.Template.Companion.TEMPLATE_TABLE
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject

@Dao
interface TemplateDao : BaseDao<Template> {
    @android.arch.persistence.room.Transaction
    @Query("SELECT * FROM $TEMPLATE_TABLE")
    fun templates(): LiveData<List<TemplateFullObject>>
}