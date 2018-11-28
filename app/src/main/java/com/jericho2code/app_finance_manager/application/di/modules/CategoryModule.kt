package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.application.di.Name
import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.CategoryDao
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
class CategoryModule {

    @Provides
    fun provideRepository(
        dao: CategoryDao,
        @Named(Name.UI_SCHEDULER)
        uiScheduler: Scheduler,
        @Named(Name.IO_SCHEDULER)
        ioScheduler: Scheduler
    ): CategoryRepository = CategoryRepository(dao, uiScheduler, ioScheduler)

    @Provides
    fun provideDao(db: AppDatabase): CategoryDao = db.categoryDao()
}