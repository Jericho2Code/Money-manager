package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.TransactionDao
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import dagger.Module
import dagger.Provides

@Module
class TransactionModule {

    @Provides
    fun provideRepository(dao: TransactionDao): TransactionRepository = TransactionRepository(dao)

    @Provides
    fun provideDao(db: AppDatabase): TransactionDao = db.transactionDao()
}