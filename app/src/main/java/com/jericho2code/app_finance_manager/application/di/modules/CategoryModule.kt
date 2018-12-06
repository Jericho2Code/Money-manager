package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.CategoryDao
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import dagger.Module
import dagger.Provides

@Module
class CategoryModule {

    @Provides
    fun provideRepository(dao: CategoryDao): CategoryRepository = CategoryRepository(dao)

    @Provides
    fun provideDao(db: AppDatabase): CategoryDao = db.categoryDao()
}