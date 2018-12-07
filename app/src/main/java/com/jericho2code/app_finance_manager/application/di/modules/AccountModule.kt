package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.AccountDao
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import dagger.Module
import dagger.Provides

@Module
class AccountModule {

    @Provides
    fun provideRepository(dao: AccountDao): AccountRepository = AccountRepository(dao)

    @Provides
    fun provideDao(db: AppDatabase): AccountDao = db.accountDao()
}