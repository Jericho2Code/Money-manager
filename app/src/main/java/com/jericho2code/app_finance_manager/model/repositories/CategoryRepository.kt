package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.jericho2code.app_finance_manager.model.database.dao.CategoryDao
import com.jericho2code.app_finance_manager.model.entity.Category
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    fun categories(): LiveData<List<Category>> = categoryDao.categories()

    @WorkerThread
    suspend fun saveCategory(category: Category) {
        withContext(Dispatchers.IO) {
            categoryDao.insert(category)
        }
    }
}