package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.TemplateDao
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import dagger.Module
import dagger.Provides

@Module
class TemplateModule {

    @Provides
    fun provideRepository(dao: TemplateDao): TemplateRepository = TemplateRepository(dao)

    @Provides
    fun provideDao(db: AppDatabase): TemplateDao = db.templateDao()
}