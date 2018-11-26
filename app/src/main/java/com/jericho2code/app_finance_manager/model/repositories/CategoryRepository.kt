package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import com.jericho2code.app_finance_manager.model.database.dao.CategoryDao
import com.jericho2code.app_finance_manager.model.entity.Category
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val uiScheduler: Scheduler,
    private val ioScheduler: Scheduler
) {

    fun categories(): LiveData<List<Category>> = categoryDao.categories()

    fun saveCategory(category: Category) = Single.fromCallable { categoryDao.insert(category) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
}