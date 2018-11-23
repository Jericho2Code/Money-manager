package com.jericho2code.app_finance_manager.model.repositories

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

    fun categories(): Single<List<Category>> = categoryDao.categories()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun saveCategory(category: Category) = Single.fromCallable { categoryDao.insert(category) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
}