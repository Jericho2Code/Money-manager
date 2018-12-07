package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.jericho2code.app_finance_manager.model.database.dao.TemplateDao
import com.jericho2code.app_finance_manager.model.entity.Template
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TemplateRepository @Inject constructor(private val templateDao: TemplateDao) {

    fun templates(): LiveData<List<TemplateFullObject>> = templateDao.templates()

    @WorkerThread
    suspend fun saveTemplate(template: Template): Long = withContext(Dispatchers.IO) {
        templateDao.insert(template)
    }

    @WorkerThread
    suspend fun updateTemplate(template: Template) = withContext(Dispatchers.IO) {
        templateDao.update(template)
    }
}